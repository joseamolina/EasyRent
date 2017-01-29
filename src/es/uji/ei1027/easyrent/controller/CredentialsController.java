package es.uji.ei1027.easyrent.controller;

import javax.servlet.http.HttpSession;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.easyrent.controller.validator.CredentialsValidator;
import es.uji.ei1027.easyrent.controller.validator.PasswordValidator;
import es.uji.ei1027.easyrent.dao.ActorDao;
import es.uji.ei1027.easyrent.dao.CredentialsDao;
import es.uji.ei1027.easyrent.domain.Actor;
import es.uji.ei1027.easyrent.domain.Alert;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Password;

@Controller
@RequestMapping("/credentials")
public class CredentialsController implements IPermissionsControl {
	
	@Autowired
	private CredentialsDao credentialsDao;
	
	@Autowired
	private ActorDao actorDao;
	
	@RequestMapping("/add")
	public String addCredentials(Model model) {
		model.addAttribute("newCredentials", new Credentials());
		return "credentials/add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processAddSubmit(Model model, @ModelAttribute("newCredentials") Credentials credentials, 
			BindingResult bindingResult, HttpSession session) {
		
		// Checking introduced data errors
		CredentialsValidator credentialsValidators = new CredentialsValidator();
		credentialsValidators.validate(credentials, bindingResult);
		if(bindingResult.hasErrors())
			return "credentials/add";
		
		// Checking if the username already exists
		if(credentialsDao.getCredentials(credentials.getUsername()) != null) {
			bindingResult.rejectValue("username", "duplicateUsername", 
					"Username already exists");
			return "credentials/add";
		}
		
		// Checking if the actor of the previous registration step was added to the session correctly
		Object obj = session.getAttribute("actor");
		if(obj == null) {
			Alert alert = new Alert();
			alert.setDescription("Something went wrong during the registration process. Try it again.");
			alert.setButton_label("Return to login screen");
			alert.setNext_url("/login.html");
			model.addAttribute("alert", alert);
			return "alert";
		}
		
		Actor actor = (Actor) obj;
		session.removeAttribute("actor");
		// Credentials id_actor corresponds to actor id
		credentials.setId_actor(actor.getId());
		// Encrypting password
		BasicPasswordEncryptor ps = new BasicPasswordEncryptor();
		credentials.setPwd(ps.encryptPassword(credentials.getPwd()));
		
		// Both elements are added to db at this point in order to prevent the existence of
		// actors without credentials
		actorDao.addActor(actor);
		credentialsDao.addCredentials(credentials);

		// Checking if is a new user or an admin is creating an user
		Credentials userCredentials = (Credentials) session.getAttribute("credentials");
		if(userCredentials != null && userCredentials.getRole().equals("administrator"))
			return "redirect:list.html";
		
		// This allows to be logged after registration
		session.setAttribute("credentials", credentials);
		
		
		
		Alert alert = new Alert();
		alert.setPage_title("You have registered correctly");
		alert.setDescription("An email has been sent to you in order to validate your"
				+ " account, which will be inactive until you follow the instructions "
				+ "provided in the email.");
		model.addAttribute("alert", alert);
		
		return "alert";
	}
	
	@RequestMapping("/changePassword/{username}")
	public String changePassword(Model model, @PathVariable String username, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {username})) {
			session.setAttribute("nextUrl", "/credentials/changePassword/"+username+".html");
			return "redirect:/login.html";
		}
		
		model.addAttribute("password", new Password());
		return "credentials/changePassword";
	}
	
	@RequestMapping(value="/changePassword/{username}", method=RequestMethod.POST)
	public String processChangePasswordSubmit(@PathVariable String username, 
			HttpSession session, @ModelAttribute Password password, BindingResult bindingResult) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {username})) {
			session.setAttribute("nextUrl", "/credentials/changePassword/"+username+".html");
			return "redirect:/login.html";
		}
		
		// Checking introduced data errors
		PasswordValidator passwordValidator = new PasswordValidator();
		passwordValidator.validate(password, bindingResult);
		if(bindingResult.hasErrors())
			return "credentials/changePassword";
		
		// Checking credentials
		Credentials credentials;
		try {
			credentials = credentialsDao.loadCredentialsByUsername(username, 
					password.getCurrent_password());
		} catch (CannotGetJdbcConnectionException e) {
			bindingResult.rejectValue("current_password", "badconn", 
					"Connection problem");
			return "credentials/changePassword";
		}
		if(credentials == null) {
			bindingResult.rejectValue("current_password", "badpwd", 
					"Incorrect password");
			return "credentials/changePassword";
		}
		
		// Encrypting password
		BasicPasswordEncryptor ps = new BasicPasswordEncryptor();
		credentials.setPwd(ps.encryptPassword(password.getNew_password()));
		
		credentialsDao.updateCredentials(credentials);
		session.setAttribute("credentials", credentials);
		return "redirect:/homePage.html";
	}

	@RequestMapping(value = "/delete/{username}")
	public String processDelete(@PathVariable String username, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		if(!authenticated(session) || !hasPermissions(session, new Object[] {username})) {
			session.setAttribute("nextUrl", "/credentials/list.html");
			return "redirect:/login.html";
		}
		
		Credentials credentialsToDelete = credentialsDao.getCredentials(username);
		credentialsDao.deleteCredentials(credentialsToDelete);
		
		// The corresponding actor is deleted too
		Actor actorToDelete = actorDao.getActor(credentialsToDelete.getId_actor());
		actorDao.deleteActor(actorToDelete);
		
		if(credentials.getRole().equals("administrator") && !credentials.getUsername().equals(username))
			return "redirect:../list.html";
		
		session.invalidate();
		return "redirect:/homePage.html";
			
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		String username = (String) args[0];
		return credentials.getUsername().equals(username) || credentials.getRole().equals("administrator");
	}
	
}
