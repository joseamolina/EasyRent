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

import es.uji.ei1027.easyrent.dao.ActorDao;
import es.uji.ei1027.easyrent.dao.CredentialsDao;
import es.uji.ei1027.easyrent.domain.Actor;
import es.uji.ei1027.easyrent.domain.Credentials;

@Controller
@RequestMapping("/credentials")
public class CredentialsAdminController implements IPermissionsControl {

	@Autowired
	private CredentialsDao credentialsDao;
	
	@Autowired
	private ActorDao actorDao;
	
	@RequestMapping("/list")
	public String listCredentials(HttpSession session, Model model) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/credentials/list.html");
			return "redirect:/login.html";
		}
		
		model.addAttribute("allCredentials", credentialsDao.getAllCredentials());
		return "credentials/list";
	}
	
	@RequestMapping(value = "/update/{username}", method = RequestMethod.GET)
	public String editCredentials(Model model, @PathVariable String username, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/credentials/update/"+username+".html");
			return "redirect:/login.html";
		}
		
		model.addAttribute("credentials", credentialsDao.getCredentials(username));

		return "credentials/update";
	}
	
	@RequestMapping(value = "/update/{username}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable String username, 
				 @ModelAttribute("credentials") Credentials credentials, 
				 BindingResult bindingResult, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/credentials/update/"+username+".html");
			return "redirect:/login.html";
		}
		
		Credentials oldCredentials = credentialsDao.getCredentials(username);
		
		// When role is changed, the corresponding actor is deleted from db and added back 
		// in order to update the related elements in tables (e.g. properties or images)
		if(!oldCredentials.getRole().equals(credentials.getRole())) {
			Actor oldActor = actorDao.getActor(oldCredentials.getId_actor());
			credentialsDao.deleteCredentials(oldCredentials);
			actorDao.deleteActor(oldActor);
			
			oldCredentials.setRole(credentials.getRole());
			actorDao.addActor(oldActor);
			credentialsDao.addCredentials(oldCredentials);
		}
	
		return "redirect:../list.html"; 
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("administrator") ;
	}
	
}
