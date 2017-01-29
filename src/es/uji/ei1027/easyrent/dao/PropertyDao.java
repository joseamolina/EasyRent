package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Consult;
import es.uji.ei1027.easyrent.domain.Property;

import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

@Repository
public class PropertyDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class PropertyMapper implements RowMapper<Property> {

		@Override
		public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
			Property property = new Property();
			property.setId(rs.getString("id"));
			property.setTitle(rs.getString("title"));
			property.setDescription(rs.getString("description"));
			property.setProperty_type(rs.getString("property_type"));
			property.setCapacity(rs.getInt("capacity"));
			property.setNum_rooms(rs.getInt("num_rooms"));
			property.setNum_baths(rs.getInt("num_baths"));
			property.setNum_beds(rs.getInt("num_beds"));
			property.setSquare_meters(rs.getInt("square_meters"));
			property.setStreet(rs.getString("street"));
			property.setNumber_home(rs.getInt("number_home"));
			property.setFloor_home(rs.getInt("floor_home"));
			property.setCity(rs.getString("city"));
			property.setDaily_price(rs.getDouble("daily_price"));
			property.setIs_active(rs.getBoolean("is_active"));
			property.setOwner(rs.getString("owner"));
			property.setTotal_rate(rs.getDouble("total_rate"));
			property.setNum_rates(rs.getInt("num_rates"));
			return property;
		}

	}

	public List<Property> getProperties() {
		return this.jdbcTemplate.query("select id, title, description, property_type, capacity, "
				+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
				+ "city, daily_price, is_active, owner, total_rate, num_rates from property;", 
				new PropertyMapper());
	}
	
	public List<Property> getActorProperties(String owner) {
		return this.jdbcTemplate.query("select id, title, description, property_type, capacity, "
				+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
				+ "city, daily_price, is_active, owner, total_rate, num_rates from property where owner= ? ;", 
				new Object[] {owner}, new PropertyMapper());
	}
	
	public List<Property> getBestRatedProperties() {
		return this.jdbcTemplate.query("select * from (select id, title, description, property_type, capacity, "
				+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
				+ "city, daily_price, is_active, owner, total_rate, num_rates from property "
				+ "order by total_rate/(CASE num_rates when 0 then 1 else num_rates end) desc limit 4) as sub order by id;", 
				new PropertyMapper());
	}
	
	public Property getProperty(String id) {
		try {
			return this.jdbcTemplate.queryForObject("select id, title, description, property_type, capacity, "
					+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
					+ "city, daily_price, is_active, owner, total_rate, num_rates from property where id = ?;", 
					new Object[] {id}, new PropertyMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public List<Property> searchProperties(Consult consult) {
		
		
		Double minDailyPrice = consult.getMinDailyPrice();	
		Double maxDailyPrice = consult.getMaxDailyPrice();
		String city = consult.getCity();
		Double rate = consult.getRate();
		Integer numberPeople = consult.getNumberPeople();

		return this.jdbcTemplate.query( "select id, title, description, property_type, capacity, "
				+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
				+ "city, daily_price, is_active, owner, total_rate, num_rates from property where city like \'"+city+"\' AND daily_price > "+ 
				minDailyPrice +" AND daily_price < "+ maxDailyPrice +" AND (total_rate/(CASE num_rates when 0 then 1 else num_rates end)) <= "+rate+" AND capacity >= "+numberPeople
				+" ; ", new PropertyMapper());
	}
	
	public void addProperty(Property property) {
		this.jdbcTemplate.update("insert into Property(id, title, description, property_type, capacity, "
				+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
				+ "city, daily_price, is_active, owner, total_rate, num_rates) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
				property.getId(), property.getTitle(), property.getDescription(), property.getProperty_type(),
				property.getCapacity(), property.getNum_rooms(), property.getNum_baths(), property.getNum_beds(),
				property.getSquare_meters(), property.getStreet(), property.getNumber_home(), property.getFloor_home(),
				property.getCity(), property.getDaily_price(), property.is_active(), property.getOwner(),
				property.getTotal_rate(), property.getNum_rates());
	}

	public void updateProperty(Property property) {
		this.jdbcTemplate.update("update Property "
				+ "set title = ?,"
				+ "description = ?,"
				+ "property_type = ?,"
				+ "capacity = ?,"
				+ "num_rooms = ?,"
				+ "num_baths = ?,"
				+ "num_beds = ?,"
				+ "square_meters = ?,"
				+ "street = ?,"
				+ "number_home = ?,"
				+ "floor_home = ?,"
				+ "city = ?,"
				+ "daily_price = ?,"
				+ "is_active = ?,"
				+ "owner = ?,"
				+ "total_rate = ?,"
				+ "num_rates = ?"
				+ "where id = ?;", 
				property.getTitle(), property.getDescription(), property.getProperty_type(),
				property.getCapacity(), property.getNum_rooms(), property.getNum_baths(), property.getNum_beds(),
				property.getSquare_meters(), property.getStreet(), property.getNumber_home(), property.getFloor_home(),
				property.getCity(), property.getDaily_price(), property.is_active(), property.getOwner(),
				property.getTotal_rate(), property.getNum_rates(), property.getId());
	}
	
	public String generatePropertyId() {
		Property prop;
		try {
			prop = this.jdbcTemplate.queryForObject("select id, title, description, property_type, capacity, "
			+ "num_rooms, num_baths, num_beds, square_meters, street, number_home, floor_home, "
			+ "city, daily_price, is_active, owner, total_rate, num_rates from property where "
			+ "id = (select max(id) from property);", new PropertyMapper());
		} catch (EmptyResultDataAccessException e) {
			prop = new Property();
			prop.setId("-1");
		}
		
		Integer id = Integer.parseInt(prop.getId());
		id++;
		return id.toString();
	}

	public void deleteProperty(Property property) {
		this.jdbcTemplate.update("DELETE from Property Where id = ?;", property.getId());
	}
	
}
