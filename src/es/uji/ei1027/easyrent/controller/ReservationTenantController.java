package es.uji.ei1027.easyrent.controller;

import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.easyrent.dao.ActorDao;
import es.uji.ei1027.easyrent.dao.PeriodDao;
import es.uji.ei1027.easyrent.dao.PropertyDao;
import es.uji.ei1027.easyrent.dao.ReservationDao;
import es.uji.ei1027.easyrent.domain.Actor;
import es.uji.ei1027.easyrent.domain.Alert;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Period;
import es.uji.ei1027.easyrent.domain.Property;
import es.uji.ei1027.easyrent.domain.Reservation;

@Controller
@RequestMapping("/reservation")
public class ReservationTenantController implements IPermissionsControl {
	
	private static final long MILLISECONDS_DAY = 24*60*60*1000;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private PeriodDao periodDao;
	
	@Autowired
	private ActorDao actorDao;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@RequestMapping("/add/{id_prop},{code_start_date},{code_finish_date}")
	public String addReservation(@PathVariable String id_prop, @PathVariable int code_start_date, 
			@PathVariable int code_finish_date, HttpSession session, Model model) {
		
		// Maybe the user authenticates at this point
		session.setAttribute("nextUrl", "/reservation/add/"+id_prop+","
			+code_start_date+","+code_finish_date+".html");
		
		// Finding the period to reserve
		Period periodToReserve = null;
		List<Period> periods = periodDao.getPropertyPeriods(id_prop);
		for(Period period:periods)
			if(period.getStart_date().hashCode() == code_start_date && 
				period.getFinish_date().hashCode() == code_finish_date)
				periodToReserve = period;
		
		// Finding the corresponding property
		Property property = propertyDao.getProperty(id_prop);
		
		// Creating a provisional reservation to show
		Reservation reservation = new Reservation();
		reservation.setNum_people(property.getCapacity());
		reservation.setStart_date(periodToReserve.getStart_date());
		reservation.setFinish_date(periodToReserve.getFinish_date());
		reservation.setTotal_amount(calculateTotalAmount(property.getDaily_price(),
				periodToReserve.getStart_date(), periodToReserve.getFinish_date()));
		reservation.setStatus("pending");
		reservation.setId_property(id_prop);
		
		session.setAttribute("periodToReserve", periodToReserve);
		model.addAttribute("reservation", reservation);
		
		return "reservation/add";
	}
	
	@RequestMapping(value="/add/{id_property},{code_start_date},{code_finish_date}", method=RequestMethod.POST)
	public String processAddSubmit(Model model, @ModelAttribute("reservation") Reservation reservation,
			@PathVariable String id_property, @PathVariable int code_start_date, 
			@PathVariable int code_finish_date, HttpSession session) {
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/reservation/add/"+id_property+","+code_start_date+","+code_finish_date+".html");
			return "redirect:/login.html";
		}
		
		// Checking if some other user has reserved the period
		Period periodToReserve = (Period) session.getAttribute("periodToReserve");
		session.removeAttribute("periodToReserve");
		if(periodDao.getperiod(periodToReserve.getStart_date(), periodToReserve.getFinish_date(), 
				periodToReserve.getId_prop()) == null) {
			
			// Creating an alert
			Alert alert = new Alert();
			alert.setPage_title("Reservation not requested");
			alert.setDescription("Sorry, another tenant may have reserved this property's period "
					+ "while you were confirming your reservation. You will look at other available periods or"
					+ " properties.");
			alert.setButton_label("Return to the property page");
			alert.setNext_url("/property/visualize/"+id_property+".html");
			model.addAttribute("alert", alert);
			return "alert";
		}
		
		periodDao.deletePeriod(periodToReserve);
		
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		Property property = propertyDao.getProperty(id_property);
		// Creating the reservation, some attributes are added later
		reservation.setStart_date(periodToReserve.getStart_date());
		reservation.setFinish_date(periodToReserve.getFinish_date());
		reservation.setTotal_amount(calculateTotalAmount(property.getDaily_price(),
				periodToReserve.getStart_date(), periodToReserve.getFinish_date()));
		reservation.setStatus("pending");
		reservation.setId_property(id_property);
		reservation.setTracking_number(reservationDao.generateReservationId());
		reservation.setApplication_timestamp(new Date());
		reservation.setTenant(credentials.getId_actor());
		
		reservationDao.addReservation(reservation);
		
		// Sending an email to the owner
		try {
			sendEmailRequest(reservation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Creating an alert
		Alert alert = new Alert();
		alert.setPage_title("Reservation requested successfully");
		alert.setDescription("You will receive an invoice and contact information via email "
				+ "when the owner accepts your request. Also, you will check the request status in "
				+ "the Requests section.");
		model.addAttribute("alert", alert);
		return "alert";
	}
	
	private double calculateTotalAmount(double daily_price, Date start_date, Date finish_date) {
		
		// The number of days is rounded
		long periodMilliseconds = finish_date.getTime() - start_date.getTime();
		return Math.ceil((periodMilliseconds / (double) MILLISECONDS_DAY)) * daily_price;
		
	}
	
	private void sendEmailRequest(Reservation reservation) throws Exception {
		Property property = propertyDao.getProperty(reservation.getId_property());
		Actor owner = actorDao.getActor(property.getOwner());
		Actor tenant = actorDao.getActor(reservation.getTenant());
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setFrom("easyrentadjos@gmail.com");
		helper.setTo(owner.getEmail());
		helper.setSubject("Reservation request");
		helper.setText("<b>"+tenant.getName()+" has requested a reservation for your property "
				+ property.getTitle()+".</b>", true);
		
		mailSender.send(message);
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		return credentials.getRole().equals("tenant");
	}

}
