package es.uji.ei1027.easyrent.controller.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.Period;

public class PeriodValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Period.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Period period = (Period) arg0;
		if(period.getStart_date() == null)
			arg1.rejectValue("start_date", "obligatory", "You must enter a value");
		else if(period.getStart_date().compareTo(new Date()) <= 0)
			arg1.rejectValue("start_date", "badPeriod", "Start date must be later than today");
		if(period.getFinish_date() == null)
			arg1.rejectValue("finish_date", "obligatory", "You must enter a value");
		else if(!(period.getStart_date().compareTo(period.getFinish_date()) < 0))
			arg1.rejectValue("finish_date", "badPeriod", "Finish date must be later than start date");
		
	}

}
