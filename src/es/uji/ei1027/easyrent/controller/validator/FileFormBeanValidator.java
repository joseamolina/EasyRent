package es.uji.ei1027.easyrent.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.easyrent.domain.FileFormBean;

public class FileFormBeanValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return FileFormBeanValidator.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		FileFormBean fileFormBean = (FileFormBean) arg0;
		if(!fileFormBean.getFile().getContentType().matches("image(.*)"))
			arg1.rejectValue("file", "badType", "Select only image files");
		
	}

}
