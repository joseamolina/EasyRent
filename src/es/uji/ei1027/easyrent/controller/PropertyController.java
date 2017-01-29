package es.uji.ei1027.easyrent.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import es.uji.ei1027.easyrent.controller.exception.ResourceNotFoundException;
import es.uji.ei1027.easyrent.controller.validator.ConsultValidator;
import es.uji.ei1027.easyrent.controller.validator.PropertyValidator;
import es.uji.ei1027.easyrent.dao.ImageDao;
import es.uji.ei1027.easyrent.dao.PeriodDao;
import es.uji.ei1027.easyrent.dao.PropertyDao;
import es.uji.ei1027.easyrent.dao.ServiceDao;
import es.uji.ei1027.easyrent.domain.Alert;
import es.uji.ei1027.easyrent.domain.Consult;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Image;
import es.uji.ei1027.easyrent.domain.Period;
import es.uji.ei1027.easyrent.domain.Property;
import es.uji.ei1027.easyrent.domain.Service;

@Controller
@RequestMapping("/property")
public class PropertyController implements IPermissionsControl {
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private ServiceDao serviceDao;
	
	@Autowired
	private PeriodDao periodDao;
	
	@RequestMapping("/list")
	public String listProperties(Model model, HttpSession session) {
		try {
			// Checking if the user has been authenticated and he has permissions
			Credentials credentials = (Credentials) session.getAttribute("credentials");
			if(authenticated(session) && hasPermissions(session, new Object[1]))
				model.addAttribute("properties", propertyDao.getActorProperties(credentials.getId_actor()));
			else {
				session.setAttribute("nextUrl", "/property/list.html");
				return "redirect:/login.html";
			}
		} catch (CannotGetJdbcConnectionException e) {}
		return "property/list";
	}
	
	@RequestMapping("/listAll")
	public String listAllProperties(Model model, @ModelAttribute("consult") Consult consult,
			HttpSession session) {
		
		// Maybe the user authenticates at this point
		session.setAttribute("nextUrl", "/property/listAll.html");
		
		model.addAttribute("properties", propertyDao.getProperties());
		model.addAttribute("consult", new Consult());

		return "property/listAll";
	}
	
	@RequestMapping(value="/listAll", method=RequestMethod.POST)
	public String processSearchSubmit(@ModelAttribute("consult") Consult consult, BindingResult bindingResult,
			Model model) {
		
		ConsultValidator consultValidator = new ConsultValidator();
		consultValidator.validate(consult, bindingResult);
		if(bindingResult.hasErrors())
			return "property/listAll";
		
		model.addAttribute("properties", propertyDao.searchProperties(consult));
		model.addAttribute("consult", new Consult());
		return "property/listAll";
	}
	
	@RequestMapping("/visualize/{id}")
	public String visualizeProperty(Model model, @PathVariable String id, HttpSession session) {
		
		if(propertyDao.getProperty(id) == null) throw new ResourceNotFoundException();
		
		// Maybe the user authenticates at this point
		session.setAttribute("nextUrl", "/property/visualize/"+id+".html");
		
		model.addAttribute("property", propertyDao.getProperty(id));
		model.addAttribute("images", imageDao.getPropertyImages(id));
		model.addAttribute("services", serviceDao.getPropertyServices(id));
		model.addAttribute("periods", periodDao.getPropertyPeriods(id));
		return "property/visualize";
	}
	
	@RequestMapping(value="/vote/{id}", method=RequestMethod.POST)
	public String voteProperty(@PathVariable String id, HttpServletRequest request, HttpSession session) {
		
		Integer vote = Integer.parseInt(request.getParameter("vote"));
		Property property = propertyDao.getProperty(id);
		property.setTotal_rate(property.getTotal_rate()+vote);
		property.setNum_rates(property.getNum_rates()+1);
		
		// To know that the user has already voted in this session
		session.setAttribute("vote"+id, vote);
		propertyDao.updateProperty(property);
		return "redirect:/property/visualize/"+id+".html";
	}
	
	@RequestMapping("/add")
	public String addProperty(Model model, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}

		model.addAttribute("property", new Property());
		return "property/add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("property") Property property,
			BindingResult bindingResult, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/property/add.html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		PropertyValidator propertyValidator = new PropertyValidator();
		propertyValidator.validate(property, bindingResult);
		if(bindingResult.hasErrors())
			return "property/add";
		
		// Generate property id
		property.setId(propertyDao.generatePropertyId());
		// Properties are active by default
		property.setIs_active(true);
		// Property owner corresponds to actor id
		property.setOwner(credentials.getId_actor());
		// Total rate and number of rates are 0
		property.setTotal_rate(0.0);
		property.setNum_rates(0);
		
		// Property is added later, jointly with images, services and periods
		session.setAttribute("property", property);
		
		return "redirect:/image/add.html";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/addFinally")
	public String submitFinallyAddProperty(Model model, HttpSession session) {
		
		// Checking if the elements of the previous registration step were added to the session correctly
		Property property = (Property) session.getAttribute("property");
		List<Image> images = (List<Image>) session.getAttribute("images");
		List<Service> services = (List<Service>) session.getAttribute("services");
		List<Period> periods = (List<Period>) session.getAttribute("periods");
		if(property == null || images == null || periods == null || services == null) {
			Alert alert = new Alert();
			alert.setDescription("Something went wrong during the add property process. Try it again.");
			alert.setButton_label("Return to list of your properties");
			alert.setNext_url("/property/list.html");
			model.addAttribute("alert", alert);
			return "alert";
		}	
		
		propertyDao.addProperty(property);
		for(Image image:images)
			imageDao.addImage(image);
		for(Service service:services)
			serviceDao.addService(service);
		for(Period period:periods)
			periodDao.addPeriod(period);
		
		session.removeAttribute("property");
		session.removeAttribute("images");
		session.removeAttribute("services");
		session.removeAttribute("periods");
		
		// Creating an alert
		Alert alert = new Alert();
		alert.setPage_title("Property added");
		alert.setDescription("Your property has been added. You can see it in <a "
				+ "href='visualize/"+property.getId()+".html'>here</a>. ");
		alert.setButton_label("Return to list of your properties");
		alert.setNext_url("/property/list.html");
		model.addAttribute("alert", alert);
		return "alert";
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
		return "property/notfound";
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("owner");
	}
	
}
