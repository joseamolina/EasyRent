package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Consult;

public class ConsultValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Consult.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		
		
	}

}
