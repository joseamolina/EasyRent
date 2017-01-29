package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Actor;

public class ActorValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Actor.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Actor actor = (Actor) arg0;
		if(actor.getId().trim().equals(""))
			arg1.rejectValue("id", "obligatory", "You must enter a value");
		if(actor.getName().trim().equals(""))
			arg1.rejectValue("name", "obligatory", "You must enter a value");
		if(actor.getSurname().trim().equals(""))
			arg1.rejectValue("surname", "obligatory", "You must enter a value");
		if(actor.getEmail().trim().equals(""))
			arg1.rejectValue("email", "obligatory", "You must enter a value");
		if(actor.getPostcode().trim().equals(""))
			arg1.rejectValue("postcode", "obligatory", "You must enter a value");
		if(actor.getPhone_number() == null)
			arg1.rejectValue("phone_number", "obligatory", "You must enter a value");
	}

}
