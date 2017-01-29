package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Period;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

@Repository
public class PeriodDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class PeriodMapper implements RowMapper<Period> {

		@Override
		public Period mapRow(ResultSet rs, int rowNum) throws SQLException {
			Period period = new Period();
			period.setStart_date(rs.getDate("start_date"));
			period.setFinish_date(rs.getDate("finish_date"));
			period.setId_prop(rs.getString("id_prop"));
			return period;
		}

	}

	public List<Period> getPeriods() {
		return this.jdbcTemplate.query("select start_date, finish_date, id_prop from period;", new PeriodMapper());
	}
	
	public Period getperiod(Date start_date, Date finish_date, String id_prop) {
		try {
			return this.jdbcTemplate.queryForObject("select start_date, finish_date, id_prop from period where "
					+ "start_date = ? and finish_date = ? and id_prop = ?;", 
				new Object[] {start_date, finish_date, id_prop}, new PeriodMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public List<Period> getPropertyPeriods(String id) {
		return this.jdbcTemplate.query("select start_date, finish_date, id_prop from period where id_prop = ?;", 
				new Object[] {id}, new PeriodMapper());
	}
	
	public void addPeriod(Period period) {
		this.jdbcTemplate.update("insert into period(start_date, finish_date, id_prop) "
				+ "values(?, ?, ?);", period.getStart_date(), period.getFinish_date(), period.getId_prop());
	}

	public void deletePeriod(Period period) {
		this.jdbcTemplate.update("DELETE from period where start_date = ? and finish_date = ? and id_prop = ?;", 
				period.getStart_date(), period.getFinish_date(), period.getId_prop());
	}
	
}
