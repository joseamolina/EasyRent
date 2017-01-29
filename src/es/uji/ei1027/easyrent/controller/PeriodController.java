package es.uji.ei1027.easyrent.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.easyrent.controller.validator.PeriodValidator;
import es.uji.ei1027.easyrent.dao.PeriodDao;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Period;
import es.uji.ei1027.easyrent.domain.Property;

@Controller
@RequestMapping("/period")
public class PeriodController implements IPermissionsControl {
	
	@Autowired
	private PeriodDao periodDao;
	
	@RequestMapping("/add")
	public String addPeriod(Model model, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
		
		if(session.getAttribute("periods") == null)
			session.setAttribute("periods", new LinkedList<>());
		
		model.addAttribute("periods", session.getAttribute("periods"));
		model.addAttribute("period", new Period());
		return "period/add";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String submitAddPeriod(@ModelAttribute("period") Period period, BindingResult bindingResult,
			HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		PeriodValidator periodValidator = new PeriodValidator();
		periodValidator.validate(period, bindingResult);
		if(bindingResult.hasErrors())
			return "period/add";
		
		Property property = (Property) session.getAttribute("property");
		// Property id_prop corresponds to property id
		period.setId_prop(property.getId());
		
		// Periods are added later, jointly with property, images and services
		List<Period> periods = (List<Period>) session.getAttribute("periods");

		// Checking if period collides
		for(Period aPeriod:periods)
			if(period.collides(aPeriod)) {
				bindingResult.rejectValue("start_date", "periodCollides", "The period collides with an existing period");
				return "period/add";
			}
		
		periods.add(period);
		return "redirect:/period/add.html";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cancel/{code1},{code2}")
	public String processAddCancel(@PathVariable int code1, @PathVariable int code2,
			HttpSession session) {
		
		List<Period> periods = (List<Period>) session.getAttribute("periods");
		Period toDelete = new Period();
		for(Period period:periods)
			if(period.getStart_date().hashCode() == code1 && period.getFinish_date().hashCode() == code2)
				toDelete = period;
		periods.remove(toDelete);
		return"redirect:/period/add.html";		
	}
	
	@RequestMapping("/update/{id_prop}")
	public String updatePeriods(Model model, HttpSession session, @PathVariable("id_prop") String id_prop) {

		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/period/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		Property property = new Property();
		property.setId(id_prop);
		model.addAttribute("property", property);
		model.addAttribute("periods", periodDao.getPropertyPeriods(id_prop));
		model.addAttribute("period", new Period());
		return "period/update";
	}
	
	@RequestMapping(value="/update/{id_prop}", method=RequestMethod.POST)
	public String processUpdateSubmit(Model model, @ModelAttribute("period") Period period, BindingResult bindingResult, 
			HttpSession session, @PathVariable("id_prop") String id_prop) {
		
		// Necessary if an error occurs in order to keep the property id in the model
		Property property = new Property();
		property.setId(id_prop);
		model.addAttribute("property", property);
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/period/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		PeriodValidator periodValidator = new PeriodValidator();
		periodValidator.validate(period, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("periods", periodDao.getPropertyPeriods(id_prop));
			return "period/update";
		}
		
		// Checking if period collides
		List<Period> periods = periodDao.getPropertyPeriods(id_prop);
		for(Period aPeriod:periods)
			if(period.collides(aPeriod)) {
				model.addAttribute("periods", periodDao.getPropertyPeriods(id_prop));
				bindingResult.rejectValue("start_date", "periodCollides", "The period collides with an existing period");
				return "period/update";
			}
		
		// Property id_prop corresponds to property id
		period.setId_prop(id_prop);
				
		periodDao.addPeriod(period);
		return "redirect:/period/update/"+id_prop+".html";
	}
	
	@RequestMapping("/delete/{code1},{code2},{id_prop}")
	public String periodDelete(@PathVariable("code1") int code1 , @PathVariable("code2") int code2,
			@PathVariable("id_prop") String id_prop, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/period/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		// Finding period by hashCode of its dates
		Period toDelete = new Period();
		List<Period> periods = periodDao.getPropertyPeriods(id_prop);
		for(Period period:periods)
			if(period.getStart_date().hashCode() == code1 && period.getFinish_date().hashCode() == code2)
				toDelete = period;
		periodDao.deletePeriod(toDelete);
		
		return "redirect:/period/update/"+id_prop+".html";
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("owner");
	}

}
