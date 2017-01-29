package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Reservation;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

@Repository
public class ReservationDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class ReservationMapper implements RowMapper<Reservation> {

		@Override
		public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reservation reservation = new Reservation();
			reservation.setTracking_number(rs.getInt("tracking_number"));
			reservation.setApplication_timestamp(rs.getDate("application_timestamp"));
			reservation.setConfirmation_timestamp(rs.getDate("confirmation_timestamp"));
			reservation.setNum_people(rs.getInt("num_people"));
			reservation.setStart_date(rs.getDate("start_date"));
			reservation.setFinish_date(rs.getDate("finish_date"));
			reservation.setTotal_amount(rs.getDouble("total_amount"));
			reservation.setStatus(rs.getString("status"));
			reservation.setId_property(rs.getString("id_property"));
			reservation.setTenant(rs.getString("tenant"));
			return reservation;
		}

	}

	public List<Reservation> getReservations() {
		return this.jdbcTemplate.query("select tracking_number, application_timestamp, confirmation_timestamp, "
				+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant "
				+ "from reservation;", new ReservationMapper());
	}
	
	public List<Reservation> getOwnerReservations(String id_owner) {
		return this.jdbcTemplate.query("select tracking_number, application_timestamp, confirmation_timestamp, "
				+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant "
				+ "from reservation where id_property in (select id from property "
				+ "where owner = ?) order by application_timestamp desc;", new Object[] {id_owner}, new ReservationMapper());
	}
	
	public List<Reservation> getTenantReservations(String id_tenant) {
		return this.jdbcTemplate.query("select tracking_number, application_timestamp, confirmation_timestamp, "
				+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant "
				+ "from reservation where tenant = ? order by application_timestamp desc;", new Object[] {id_tenant}, 
				new ReservationMapper());
	}
	
	public List<Reservation> getOwnerPendingReservations(String id_owner) {
		return this.jdbcTemplate.query("select tracking_number, application_timestamp, confirmation_timestamp, "
				+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant "
				+ "from reservation where id_property in (select id from property "
				+ "where owner = ?) and status = 'pending';", new Object[] {id_owner}, new ReservationMapper());
	}
	
	public Reservation getReservation(int tracking_number) {
		return this.jdbcTemplate.queryForObject("select tracking_number, application_timestamp, confirmation_timestamp, "
				+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant "
				+ "from reservation where tracking_number = ?;", 
				new Object[] {tracking_number}, new ReservationMapper());
	}
	
	public void addReservation(Reservation reservation) {
		this.jdbcTemplate.update("insert into reservation(tracking_number, application_timestamp, confirmation_timestamp, "
				+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
				reservation.getTracking_number(), reservation.getApplication_timestamp(), 
				reservation.getConfirmation_timestamp(), reservation.getNum_people(), reservation.getStart_date(),
				reservation.getFinish_date(), reservation.getTotal_amount(), reservation.getStatus(), 
				reservation.getId_property(), reservation.getTenant());
	}

	public void updateReservation(Reservation reservation) {
		this.jdbcTemplate.update("update reservation "
				+ "set application_timestamp = ?,"
				+ "confirmation_timestamp = ?,"
				+ "num_people = ?,"
				+ "start_date = ?,"
				+ "finish_date = ?,"
				+ "total_amount = ?,"
				+ "status = ?,"
				+ "id_property = ?,"
				+ "tenant =?"
				+ "where tracking_number = ?;", 
				reservation.getApplication_timestamp(), 
				reservation.getConfirmation_timestamp(), reservation.getNum_people(), reservation.getStart_date(),
				reservation.getFinish_date(), reservation.getTotal_amount(), reservation.getStatus(), 
				reservation.getId_property(), reservation.getTenant(), reservation.getTracking_number());
	}
	
	public Integer generateReservationId() {
		Reservation reservation;
		try {
			reservation = this.jdbcTemplate.queryForObject("select tracking_number, application_timestamp, confirmation_timestamp, "
					+ "num_people, start_date, finish_date, total_amount, status, id_property, tenant "
					+ "from reservation where tracking_number = (select max(tracking_number) from reservation);", new ReservationMapper());
		} catch (EmptyResultDataAccessException e) {
			reservation = new Reservation();
			reservation.setTracking_number(-1);
		}
		
		Integer tracking_number = reservation.getTracking_number();
		tracking_number++;
		return tracking_number;
	}

	public void deleteActor(Reservation reservation) {
		this.jdbcTemplate.update("DELETE from reservation Where tracking_number = ?;", 
				reservation.getTracking_number());
	}
	
}
