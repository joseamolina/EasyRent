package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Invoice;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

@Repository
public class InvoiceDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class InvoiceMapper implements RowMapper<Invoice> {

		@Override
		public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
			Invoice invoice = new Invoice();
			invoice.setIn_num(rs.getInt("in_num"));
			invoice.setInvoice_date(rs.getDate("invoice_date"));
			invoice.setId_res(rs.getInt("id_res"));
			return invoice;
		}

	}

	public List<Invoice> getInvoices() {
		return this.jdbcTemplate.query("select in_num, invoice_date, id_res from invoice;", new InvoiceMapper());
	}
	
	public Invoice getInvoice(String in_num) {
		return this.jdbcTemplate.queryForObject("select in_num, invoice_date, id_res from invoice where in_num = ?;", 
				new Object[] {in_num}, new InvoiceMapper());
	}
	
	public void addInvoice(Invoice invoice) {
		this.jdbcTemplate.update("insert into invoice(in_num, invoice_date, id_res) values(?, ?, ?);",
				invoice.getIn_num(), invoice.getInvoice_date(), invoice.getId_res());
	}

	public void updateInvoice(Invoice invoice) {
		this.jdbcTemplate.update("update invoice "
				+ "set invoice_date = ?,"
				+ "id_res = ?"
				+ "where in_num = ?;", 
				invoice.getInvoice_date(), invoice.getId_res(), invoice.getIn_num());
	}
	
	public Integer generateInvoiceId() {
		Invoice invoice;
		try {
			invoice = this.jdbcTemplate.queryForObject("select in_num, invoice_date, id_res "
					+ "from invoice where in_num = (select max(in_num) from invoice);", new InvoiceMapper());
		} catch (EmptyResultDataAccessException e) {
			invoice = new Invoice();
			invoice.setIn_num(-1);
		}
		Integer in_num = invoice.getIn_num();
		in_num++;
		return in_num;
	}

	public void deleteInvoice(Invoice invoice) {
		this.jdbcTemplate.update("DELETE from invoice Where in_num = ?;", invoice.getIn_num());
	}
	
}
