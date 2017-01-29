package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Credentials;

public class CredentialsValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Credentials.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Credentials credentials = (Credentials) arg0;
		if(credentials.getUsername().trim().equals(""))
			arg1.rejectValue("username", "obligatory", "You must enter a value");
		if(credentials.getPwd().trim().equals(""))
			arg1.rejectValue("pwd", "obligatory", "You must enter a value");
	}

}
