package es.uji.ei1027.easyrent.domain;

import java.util.Date;

public class Reservation {
	private Integer tracking_number;
	private Date application_timestamp;
	private Date confirmation_timestamp;
	private Integer num_people;
	private Date  start_date;
	private Date finish_date;
	private Double total_amount;
	private String status;
	private String id_property;
	private String tenant;
	
	public Reservation() {
		super();
	}
	
	public Reservation(Integer tracking_number, Date application_timestap, Date confirmation_timestap, Integer num_people,
			Date start_date, Date finish_date, Double total_amont, String status, String id_property, String tenant) {
		super();
		this.tracking_number = tracking_number;
		this.application_timestamp = application_timestap;
		this.confirmation_timestamp = confirmation_timestap;
		this.num_people = num_people;
		this.start_date = start_date;
		this.finish_date = finish_date;
		this.total_amount = total_amont;
		this.status = status;
		this.id_property = id_property;
		this.tenant = tenant;
	}
	
	public Integer getTracking_number() {
		return tracking_number;
	}
	
	public void setTracking_number(Integer tracking_number) {
		this.tracking_number = tracking_number;
	}
	
	public Date getApplication_timestamp() {
		return application_timestamp;
	}
	
	public void setApplication_timestamp(Date application_timestap) {
		this.application_timestamp = application_timestap;
	}
	
	public Date getConfirmation_timestamp() {
		return confirmation_timestamp;
	}
	
	public void setConfirmation_timestamp(Date confirmation_timestap) {
		this.confirmation_timestamp = confirmation_timestap;
	}
	
	public Integer getNum_people() {
		return num_people;
	}
	
	public void setNum_people(Integer num_people) {
		this.num_people = num_people;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getFinish_date() {
		return finish_date;
	}
	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	
	public void setTotal_amount(Double total_amont) {
		this.total_amount = total_amont;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getId_property() {
		return id_property;
	}
	
	public void setId_property(String id_property) {
		this.id_property = id_property;
	}
	
	public String getTenant() {
		return tenant;
	}
	
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	@Override
	public String toString() {
		return "Reservation [tracking_number=" + tracking_number + ", application_timestamp=" + application_timestamp
				+ ", confirmation_timestamp=" + confirmation_timestamp + ", num_people=" + num_people 
				+ ", start_date=" + start_date + ", finish_date=" + finish_date + ", total_amount=" 
				+ total_amount + ", status=" + status + ", id_property=" + id_property + ", tenant=" + tenant +"]";
	}
	
}
