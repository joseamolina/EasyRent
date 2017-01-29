package es.uji.ei1027.easyrent.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.easyrent.controller.validator.CredentialsValidator;
import es.uji.ei1027.easyrent.dao.CredentialsDao;
import es.uji.ei1027.easyrent.domain.Credentials;

@Controller
public class LoginController {
	
	@Autowired
	private CredentialsDao credentialsDao;
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("credentials") Credentials credentials,
			BindingResult bindingResult, HttpSession session) {
		
		// Checking introduced data errors
		CredentialsValidator credentialsValidator = new CredentialsValidator();
		credentialsValidator.validate(credentials, bindingResult);
		if(bindingResult.hasErrors())
			return "login";
		
		// Checking credentials
		try {
			credentials = credentialsDao.loadCredentialsByUsername(credentials.getUsername(), 
					credentials.getPwd());
		} catch (CannotGetJdbcConnectionException e) {
			bindingResult.rejectValue("username", "badconn", 
					"Connection problem");
			return "login";
		}
		if(credentials == null) {
			bindingResult.rejectValue("pwd", "badpwd", 
					"Incorrect password or user does not exist");
			return "login";
		}
		
		// This means to be logged
		session.setAttribute("credentials", credentials);
		
		// Redirection to an indicated URL, home by default
		if(session.getAttribute("nextUrl") == null) {
			return "redirect:/homePage.html";
		}
		String nextUrl = (String) session.getAttribute("nextUrl");
		session.removeAttribute("nextUrl");
		return "redirect:"+nextUrl;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/homePage.html";
	}
	
}
