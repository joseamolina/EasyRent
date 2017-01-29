package es.uji.ei1027.easyrent.domain;

public class Password {
	private String current_password;
	private String new_password;
	
	public Password() {
		super();
	}
	
	public Password(String current_password, String new_password) {
		this.current_password = current_password;
		this.new_password = new_password;
	}
	
	public String getCurrent_password() {
		return current_password;
	}
	
	public void setCurrent_password(String current_password) {
		this.current_password = current_password;
	}
	
	public String getNew_password() {
		return new_password;
	}
	
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

}
