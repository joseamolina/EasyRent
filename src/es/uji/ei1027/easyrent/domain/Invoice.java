package es.uji.ei1027.easyrent.domain;

import java.util.Date;

public class Invoice {
	private Integer in_num;
	private Date invoice_date;
	private Integer id_res;
	
	public Invoice() {
		super();
	}
	
	public Invoice(Integer in_num, Date invoice_date, Integer id_res) {
		super();
		this.in_num = in_num;
		this.invoice_date = invoice_date;
		this.id_res = id_res;
	}
	
	public Integer getIn_num() {
		return in_num;
	}
	
	public void setIn_num(Integer in_num) {
		this.in_num = in_num;
	}
	
	public Date getInvoice_date() {
		return invoice_date;
	}
	
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	
	public Integer getId_res() {
		return id_res;
	}
	
	public void setId_res(Integer id_res) {
		this.id_res = id_res;
	}
	
	@Override
	public String toString() {
		return "Invoice [in_num=" + in_num + ", invoice_date=" + invoice_date
				+ ", id_res=" + id_res + "]";
	}

}
