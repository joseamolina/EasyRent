package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Service;

public class ServiceValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Service.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Service service = (Service) arg0;
		if(service.getName().trim().equals(""))
			arg1.rejectValue("name", "obligatory", "You must enter a value");
	}

}
