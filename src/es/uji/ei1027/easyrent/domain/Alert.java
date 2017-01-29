package es.uji.ei1027.easyrent.domain;

public class Alert {
	private String page_title;
	private String description;
	private String next_url;
	private String button_label;
	
	public Alert() {
		super();
		page_title = "Alert";
		description = "Something has occurred";
		next_url ="/homePage.html";
		button_label="Return to Home page";
	}
	
	public String getPage_title() {
		return page_title;
	}
	
	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getNext_url() {
		return next_url;
	}
	
	public void setNext_url(String next_url) {
		this.next_url = next_url;
	}
	
	public String getButton_label() {
		return button_label;
	}
	
	public void setButton_label(String button_label) {
		this.button_label = button_label;
	}
	
}
