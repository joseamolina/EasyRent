package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Password;

public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Password.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Password password = (Password) arg0;
		if(password.getCurrent_password().trim().equals(""))
			arg1.rejectValue("current_password", "obligatory", "You must enter a value");
		if(password.getNew_password().trim().equals(""))
			arg1.rejectValue("new_password", "obligatory", "You must enter a value");
	}

}
