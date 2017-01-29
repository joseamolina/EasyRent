package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Service;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

@Repository
public class ServiceDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class ServiceMapper implements RowMapper<Service> {

		@Override
		public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
			Service service = new Service();
			service.setName(rs.getString("name"));
			service.setId_prop(rs.getString("id_prop"));
			return service;
		}

	}

	public List<Service> getServices() {
		return this.jdbcTemplate.query("select name, id_prop from service;", new ServiceMapper());
	}
	
	public List<Service> getPropertyServices(String id) {
		return this.jdbcTemplate.query("select name, id_prop from service where id_prop = ?;", 
				new Object[] {id}, new ServiceMapper());
	}
	
	public Service getService(String id, String name) {
		try {
			return this.jdbcTemplate.queryForObject("select name, id_prop from service "
					+ "where id_prop = ? and name = ?;", new Object[] {id, name}, new ServiceMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void addService(Service service) {
		this.jdbcTemplate.update("insert into service(name, id_prop) values(?, ?);",
				service.getName(), service.getId_prop());
	}

	public void deleteService(Service service) {
		this.jdbcTemplate.update("DELETE from service Where name = ? and id_prop = ?;", 
				service.getName(), service.getId_prop());
	}
	
}
