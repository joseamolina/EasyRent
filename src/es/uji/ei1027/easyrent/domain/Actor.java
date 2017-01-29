package es.uji.ei1027.easyrent.domain;

import java.util.Date;

public class Actor {
	private String id;
	private String name;
	private String surname;
	private String email;
	private String postcode;
	private Date registration_date;
	private Integer phone_number;
	private Boolean is_active;
	
	public Actor() {
		super();
	}
	
	public Actor(String id, String name, String surname, String email, String postcode, Date registration_date,
			Integer telephone_number, Boolean is_active) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.postcode = postcode;
		this.registration_date = registration_date;
		this.phone_number = telephone_number;
		this.is_active = is_active;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public Date getRegistration_date() {
		return registration_date;
	}
	
	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	
	public Integer getPhone_number() {
		return phone_number;
	}
	
	public void setPhone_number(Integer telephone_number) {
		this.phone_number = telephone_number;
	}
	
	public Boolean is_active() {
		return is_active;
	}
	
	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}
	
	@Override
	public String toString() {
		return "Actor [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", postcode=" + postcode
				+ ", registration_date=" + registration_date
				+ ", phone_number=" + phone_number + ", is_active=" + is_active+"]";
	}

}
