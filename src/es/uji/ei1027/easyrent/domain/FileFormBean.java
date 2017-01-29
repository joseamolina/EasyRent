package es.uji.ei1027.easyrent.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileFormBean {
	
	private CommonsMultipartFile file;
	private String caption;
	
	public FileFormBean() {
		super();
	}
	
	public CommonsMultipartFile getFile() {
		return file;
	}
	
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}

}
