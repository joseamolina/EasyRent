package es.uji.ei1027.easyrent.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.easyrent.controller.validator.ActorValidator;
import es.uji.ei1027.easyrent.dao.ActorDao;
import es.uji.ei1027.easyrent.domain.Actor;
import es.uji.ei1027.easyrent.domain.Credentials;

@Controller
@RequestMapping("/actor")
public class ActorController implements IPermissionsControl{
	
	@Autowired
	private ActorDao actorDao;

	@RequestMapping("/add")
	public String addActor(Model model) {
		model.addAttribute("actor", new Actor());
		return "actor/add";
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("actor") Actor actor, 
			BindingResult bindingResult, HttpSession session) {
		
		// Checking introduced data errors
		ActorValidator actorValidator = new ActorValidator();
		actorValidator.validate(actor, bindingResult);
		if(bindingResult.hasErrors())
			return "actor/add";

		// Checking if the id already exists
		if(actorDao.getActor(actor.getId()) != null) {
			bindingResult.rejectValue("id", "duplicateId", 
					"Id already exists");
			return "actor/add";
		}
		
		// Actors are active by default
		actor.setIs_active(true);
		// Current date as registration date
		actor.setRegistration_date(new Date());
		
		// Actor is added later, jointly with credentials
		session.setAttribute("actor", actor);
		
		return "redirect:/credentials/add.html";
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String editActor(Model model, @PathVariable("id") String id, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!(authenticated(session) && hasPermissions(session, new Object[] {id}))) {
			session.setAttribute("nextUrl", "/actor/update/"+id+".html");
			return "redirect:/login.html";
		}
		
		model.addAttribute("actor", actorDao.getActor(id));
		return "actor/update";
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable("id") String id, 
				 @ModelAttribute("actor") Actor actor, 
				 BindingResult bindingResult, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!(authenticated(session) && hasPermissions(session, new Object[] {id}))) {
			session.setAttribute("nextUrl", "/actor/update/"+id+".html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		ActorValidator actorValidator = new ActorValidator();
		actorValidator.validate(actor, bindingResult);
		if (bindingResult.hasErrors()) 
			return "actor/update";
		
		// Set is_active and registration date
		Actor oldActor = actorDao.getActor(actor.getId());
		actor.setIs_active(oldActor.is_active());
		actor.setRegistration_date(oldActor.getRegistration_date());
		
		actorDao.updateActor(actor);
		return "redirect:/homePage.html";
	}

	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		String id = (String) args[0];
		return credentials.getId_actor().equals(id);
	}
	
}
