package es.uji.ei1027.easyrent.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.easyrent.domain.Image;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ImageDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final class ImageMapper implements RowMapper<Image> {

		@Override
		public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
			Image image = new Image();
			image.setHref(rs.getString("href"));
			image.setCaption(rs.getString("caption"));
			image.setId_prop(rs.getString("id_prop"));
			return image;
		}

	}

	public List<Image> getImages() {
		return this.jdbcTemplate.query("select href, caption, id_prop from image;", new ImageMapper());
	}
	
	public List<Image> getPropertyImages(String id) {
		return this.jdbcTemplate.query("select href, caption, id_prop from image where id_prop = ?;", 
				new Object[] {id}, new ImageMapper());
	}
	
	public Image getImage(String href) {
		try {
			return this.jdbcTemplate.queryForObject("select href, caption, id_prop from image where href = ?;", 
					new Object[] {href}, new ImageMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void addImage(Image image) {
		this.jdbcTemplate.update("insert into image(href, caption, id_prop) values(?, ?, ?);",
				image.getHref(), image.getCaption(), image.getId_prop());
	}

	public void updateImage(Image image) {
		this.jdbcTemplate.update("update image "
				+ "set caption = ?,"
				+ "id_prop = ?"
				+ "where href = ?;", 
				image.getCaption(), image.getId_prop(), image.getHref());
	}

	public void deleteImage(Image image) {
		this.jdbcTemplate.update("DELETE from image Where href = ?;", image.getHref());
	}

}
