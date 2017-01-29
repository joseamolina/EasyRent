package es.uji.ei1027.easyrent.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.easyrent.controller.validator.PropertyValidator;
import es.uji.ei1027.easyrent.dao.PropertyDao;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Property;

@Controller
@RequestMapping("/property")
public class PropertyManagementController implements IPermissionsControl {

	@Autowired
	private PropertyDao propertyDao;
	
	@RequestMapping("/update/{id}")
	public String editProperty(Model model, @PathVariable("id") String id, HttpSession session) {
		
		Property property = propertyDao.getProperty(id);
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {property.getOwner()})) {
			session.setAttribute("nextUrl", "/property/update/"+id+".html");
			return "redirect:/login.html";
		}
		
		model.addAttribute("property", property);
		return "property/update";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public String processUpdateSubmit(Model model, @PathVariable("id") String id, 
			 @ModelAttribute("property") Property property,
			 BindingResult bindingResult, HttpSession session) {
		
		// Necessary if an error occurs in order to keep the property id in the model
		Property oldProperty = propertyDao.getProperty(id);
		model.addAttribute("property", oldProperty);
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {oldProperty.getOwner()})) {
			session.setAttribute("nextUrl", "/property/update/"+id+".html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		PropertyValidator propertyValidator = new PropertyValidator();
		propertyValidator.validate(property, bindingResult);
		if (bindingResult.hasErrors()) 
			return "property/update";
		
		// Set id, is_active, num_rates and total_rate
		property.setId(oldProperty.getId());
		property.setIs_active(oldProperty.is_active());
		property.setOwner(oldProperty.getOwner());
		property.setTotal_rate(oldProperty.getTotal_rate());
		property.setNum_rates(oldProperty.getNum_rates());
		
		propertyDao.updateProperty(property);
		return "redirect:/property/list.html";
	}
	
	@RequestMapping("/delete/{id}")
	public String processDelete(@PathVariable String id, HttpSession session) {
		
		Property property = propertyDao.getProperty(id);
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {property.getOwner()})) {
			session.setAttribute("nextUrl", "/property/list.html");
			return "redirect:/login.html";
		}
		
		// Corresponding images, services are deleted by cascade
		propertyDao.deleteProperty(property);
		
		return "redirect:/property/list.html";
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		String owner_id = (String) args[0];
		return credentials.getId_actor().equals(owner_id);
	}
	
}
