package es.uji.ei1027.easyrent.domain;

import java.util.Date;

public class Consult {

	private Date dateEntry;
	private Date dateExit;
	private Double minDailyPrice;
	private Double maxDailyPrice;
	private Integer numberPeople;
	private Double rate;
	private String city;
	
	public Consult() {
		super();
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
	}

	public Date getDateExit() {
		return dateExit;
	}

	public void setDateExit(Date dateExit) {
		this.dateExit = dateExit;
	}

	public Double getMinDailyPrice() {
		return minDailyPrice;
	}

	public void setMinDailyPrice(Double minDailyPrice) {
		this.minDailyPrice = minDailyPrice;
	}

	public Double getMaxDailyPrice() {
		return maxDailyPrice;
	}

	public void setMaxDailyPrice(Double maxDailyPrice) {
		this.maxDailyPrice = maxDailyPrice;
	}

	public Integer getNumberPeople() {
		return numberPeople;
	}

	public void setNumberPeople(Integer numberPeople) {
		this.numberPeople = numberPeople;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
