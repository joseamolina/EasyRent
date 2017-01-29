package es.uji.ei1027.easyrent.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.easyrent.dao.ImageDao;
import es.uji.ei1027.easyrent.dao.PropertyDao;
import es.uji.ei1027.easyrent.dao.ReservationDao;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Property;

@Controller
public class HomePageController implements IPermissionsControl {
	
	@Autowired
	PropertyDao propertyDao;
	
	@Autowired
	ImageDao imageDao;
	
	@Autowired
	ReservationDao reservationDao;

	@RequestMapping("/homePage")
	public String homePage(Model model, HttpSession session) {

		try {
			// Initializes variables for building the carousel
			List<Property> bestProperties = propertyDao.getBestRatedProperties();
			model.addAttribute("bestProperties", bestProperties);
			
			if(authenticated(session) && hasPermissions(session, new Object[1])) {
				Credentials credentials = (Credentials) session.getAttribute("credentials");
				Integer numPendingReservations = reservationDao.getOwnerPendingReservations(credentials.getId_actor()).size();
				
				// If the user is owner, all views need this attribute in the navigation bar
				session.setAttribute("numPendingReservations", numPendingReservations);
			}
		} catch (CannotGetJdbcConnectionException e) {}
		
		return "homePage";
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("owner");
	}
	
}
