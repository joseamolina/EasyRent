package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Actor;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

@Repository
public class ActorDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class ActorMapper implements RowMapper<Actor> {

		@Override
		public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
			Actor actor = new Actor();
			actor.setId(rs.getString("id"));
			actor.setName(rs.getString("name"));
			actor.setSurname(rs.getString("surname"));
			actor.setEmail(rs.getString("email"));
			actor.setPostcode(rs.getString("postcode"));
			actor.setRegistration_date(rs.getDate("registration_date"));
			actor.setPhone_number(rs.getInt("phone_number"));
			actor.setIs_active(rs.getBoolean("is_active"));
			return actor;
		}

	}

	public List<Actor> getActors() {
		return this.jdbcTemplate.query("select id, name, surname, email, postcode, registration_date, "
				+ "phone_number, is_active from actor;", new ActorMapper());
	}
	
	public Actor getActor(String id) {
		try {
			return this.jdbcTemplate.queryForObject("select id, name, surname, email, postcode, registration_date, "
					+ "phone_number, is_active from actor where id = ?;", 
					new Object[] {id}, new ActorMapper());
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public void addActor(Actor actor) {
		this.jdbcTemplate.update("insert into Actor(id, name, surname, email, postcode, registration_date, "
				+ "phone_number, is_active) values(?, ?, ?, ?, ?, ?, ?, ?);",
				actor.getId(), actor.getName(), actor.getSurname(), actor.getEmail(), actor.getPostcode(), 
				actor.getRegistration_date(), actor.getPhone_number(), actor.is_active());
	}

	public void updateActor(Actor actor) {
		this.jdbcTemplate.update("update Actor "
				+ "set name = ?,"
				+ "surname = ?,"
				+ "email = ?,"
				+ "postcode = ?,"
				+ "registration_date = ?,"
				+ "phone_number = ?,"
				+ "is_active = ?"
				+ "where id = ?;", 
				actor.getName(), actor.getSurname(), actor.getEmail(), actor.getPostcode(), 
				actor.getRegistration_date(), actor.getPhone_number(), actor.is_active(), actor.getId());
	}

	public void deleteActor(Actor actor) {
		this.jdbcTemplate.update("DELETE from Actor Where id = ?;", actor.getId());
	}
	
}
