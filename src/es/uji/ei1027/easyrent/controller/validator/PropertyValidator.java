package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Property;

public class PropertyValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Property.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Property property = (Property) arg0;
		if(property.getTitle().trim().equals(""))
			arg1.rejectValue("title", "obligatory", "You must enter a value");
		if(property.getStreet().trim().equals(""))
			arg1.rejectValue("street", "obligatory", "You must enter a value");
		if(property.getCity().trim().equals(""))
			arg1.rejectValue("city", "obligatory", "You must enter a value");
	}

}
