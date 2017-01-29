package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Credentials;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

@Repository
public class CredentialsDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class CredentialsMapper implements RowMapper<Credentials> {

		@Override
		public Credentials mapRow(ResultSet rs, int rowNum) throws SQLException {
			Credentials credentials = new Credentials();
			credentials.setId_actor(rs.getString("id_actor"));
			credentials.setUsername(rs.getString("username"));
			credentials.setPwd(rs.getString("pwd"));
			credentials.setRole(rs.getString("role"));
			return credentials;
		}

	}

	public List<Credentials> getAllCredentials() {
		return this.jdbcTemplate.query("select id_actor, username, pwd, role from credentials;", 
				new CredentialsMapper());
	}
	
	public Credentials getCredentials(String username) {
		try {
			return this.jdbcTemplate.queryForObject("select id_actor, username, pwd, role from credentials where "
					+ "username = ?;", new Object[] {username}, new CredentialsMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Credentials loadCredentialsByUsername(String username, String pwd) {
		
		Credentials credentials = getCredentials(username.trim());
		if(credentials == null)
			return null; // User does not exist
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		if(passwordEncryptor.checkPassword(pwd, credentials.getPwd()))
			return credentials; // Correct login
		return null; // Bad login
	}
	
	public void addCredentials(Credentials credentials) {
		this.jdbcTemplate.update("insert into credentials(id_actor, username, pwd, role) "
				+ "values(?, ?, ?, ?);", credentials.getId_actor(), credentials.getUsername(), 
				credentials.getPwd(), credentials.getRole());
	}

	public void updateCredentials(Credentials credentials) {
		this.jdbcTemplate.update("update Credentials "
				+ "set id_actor = ?,"
				+ "pwd = ?,"
				+ "role = ?"
				+ "where username = ?;", 
				credentials.getId_actor(), credentials.getPwd(), credentials.getRole(), 
				credentials.getUsername());
	}

	public void deleteCredentials(Credentials credentials) {
		this.jdbcTemplate.update("DELETE from Credentials Where username = ?;", credentials.getUsername());
	}
	
}
