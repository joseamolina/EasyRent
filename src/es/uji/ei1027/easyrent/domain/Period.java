package es.uji.ei1027.easyrent.domain;

import java.util.Date;

public class Period {
	private Date start_date;
	private Date finish_date;
	private String id_prop;
	
	public Period() {
		super();
	}
	
	public Period(Date start_date, Date finish_date, String id_prop) {
		super();
		this.start_date = start_date;
		this.finish_date = finish_date;
		this.id_prop = id_prop;
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
	
	public String getId_prop() {
		return id_prop;
	}
	
	public void setId_prop(String id_prop) {
		this.id_prop = id_prop;
	}
	
	public boolean collides(Period period) {
		// Assumed that in a period, finish date is later than start date
		return !(this.finish_date.compareTo(period.start_date) < 0 ||
				period.finish_date.compareTo(this.start_date) < 0);
	}
	
	@Override
	public String toString() {
		return "Period [start_date=" + start_date + ", finish_date=" + finish_date
				+ ", id_prop=" + id_prop + "]";
	}

}
