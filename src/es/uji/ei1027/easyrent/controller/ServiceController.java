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

import es.uji.ei1027.easyrent.controller.validator.ServiceValidator;
import es.uji.ei1027.easyrent.dao.ServiceDao;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Property;
import es.uji.ei1027.easyrent.domain.Service;

@Controller
@RequestMapping("/service")
public class ServiceController implements IPermissionsControl {

	@Autowired
	private ServiceDao serviceDao;
	
	@RequestMapping("/add")
	public String addService(Model model, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
		
		if(session.getAttribute("services") == null)
			session.setAttribute("services", new LinkedList<Service>());
		
		model.addAttribute("service", new Service());
		return "service/add";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute Service service, BindingResult bindingResult,
			HttpSession session) {

		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		ServiceValidator serviceValidator = new ServiceValidator();
		serviceValidator.validate(service, bindingResult);
		if(bindingResult.hasErrors())
			return "service/add";
		
		Property property = (Property) session.getAttribute("property");
		// Service id_prop corresponds to property id
		service.setId_prop(property.getId());
		
		// Services are added later, jointly with property, images and periods
		List<Service> services = (List<Service>) session.getAttribute("services");
		
		// Checking if the service is duplicated
		for(Service aService:services)
			if(aService.getName().equals(service.getName())) {
				bindingResult.rejectValue("name", "duplicated", "The service has been already added");
				return "service/add";
			}
		
		services.add(service);
		return "redirect:/service/add.html";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/cancel/{code}")
	public String processAddCancel(@PathVariable int code, HttpSession session) {
		
		List<Service> services = (List<Service>) session.getAttribute("services");
		Service toDelete = new Service();
		for(Service service:services)
			if(service.getName().hashCode() == code)
				toDelete = service;
		services.remove(toDelete);
		
		return "redirect:/service/add.html";
	}
	
	@RequestMapping("/update/{id_prop}")
	public String updateServices(Model model, HttpSession session, @PathVariable("id_prop") String id_prop) {

		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/service/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		Property property = new Property();
		property.setId(id_prop);
		model.addAttribute("property", property);
		model.addAttribute("services", serviceDao.getPropertyServices(id_prop));
		model.addAttribute("service", new Service());
		return "service/update";
	}
	
	@RequestMapping(value="/update/{id_prop}", method=RequestMethod.POST)
	public String processUpdateSubmit(Model model, @ModelAttribute("service") Service service, BindingResult bindingResult, 
			HttpSession session, @PathVariable("id_prop") String id_prop) {
		
		// Necessary if an error occurs in order to keep the property id in the model
		Property property = new Property();
		property.setId(id_prop);
		model.addAttribute("property", property);
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/service/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		ServiceValidator serviceValidator = new ServiceValidator();
		serviceValidator.validate(service, bindingResult);
		if(bindingResult.hasErrors()) {
			session.setAttribute("services", serviceDao.getPropertyServices(id_prop));
			return "service/update";
		}
		
		// Checking if the service already exists
		if(serviceDao.getService(id_prop, service.getName()) != null) {
			session.setAttribute("services", serviceDao.getPropertyServices(id_prop));
			bindingResult.rejectValue("name", "duplicateService", 
					"Service already exists");
			return "service/update";
		}
		session.removeAttribute("services");
		
		service.setId_prop(id_prop);
		serviceDao.addService(service);
		return "redirect:/service/update/"+id_prop+".html";
	}
	
	@RequestMapping("/delete/{name},{id_prop}")
	public String processDelete(@PathVariable("name") String name , @PathVariable("id_prop") String id_prop,
			HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[0])) {
			session.setAttribute("nextUrl", "/service/update/"+id_prop+".html");
			return "redirect:/login.html";
		}
		
		Service service = new Service();
		service.setName(name);
		service.setId_prop(id_prop);
		serviceDao.deleteService(service);
		
		return "redirect:/service/update/"+id_prop+".html";
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("owner");
	}
	
}
