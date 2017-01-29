package es.uji.ei1027.easyrent.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.uji.ei1027.easyrent.dao.ActorDao;
import es.uji.ei1027.easyrent.dao.InvoiceDao;
import es.uji.ei1027.easyrent.dao.PeriodDao;
import es.uji.ei1027.easyrent.dao.PropertyDao;
import es.uji.ei1027.easyrent.dao.ReservationDao;
import es.uji.ei1027.easyrent.domain.Actor;
import es.uji.ei1027.easyrent.domain.Alert;
import es.uji.ei1027.easyrent.domain.Credentials;
import es.uji.ei1027.easyrent.domain.Invoice;
import es.uji.ei1027.easyrent.domain.Period;
import es.uji.ei1027.easyrent.domain.Property;
import es.uji.ei1027.easyrent.domain.Reservation;

@Controller
@RequestMapping("/reservation")
public class ReservationOwnerController implements IPermissionsControl {
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private PeriodDao periodDao;
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private ActorDao actorDao;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@RequestMapping("/list")
	public String listReservations(Model model, HttpSession session) {
		// This resource is common for owners and tenants
		
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[1])) {
			session.setAttribute("nextUrl", "/reservation/list.html");
			return "redirect:/login.html";
		}
		
		if(credentials.getRole().equals("owner")) {
			model.addAttribute("reservations", reservationDao.getOwnerReservations(credentials.getId_actor()));
			session.setAttribute("numPendingReservations", reservationDao.getOwnerPendingReservations(
					credentials.getId_actor()).size());
		}
		else
			model.addAttribute("reservations", reservationDao.getTenantReservations(credentials.getId_actor()));
		
		return "reservation/list";
	}
	
	@RequestMapping("/accept/{tracking_number}")
	public String acceptReservation(Model model, HttpSession session, @PathVariable Integer tracking_number) {
		
		Reservation reservation = reservationDao.getReservation(tracking_number);
		Property property = propertyDao.getProperty(reservation.getId_property());
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {property.getOwner()})) {
			session.setAttribute("nextUrl", "/reservation/accept/"+tracking_number+".html");
			return "redirect:/login.html";
		}
		
		reservation.setStatus("accepted");
		// Current date as confirmation date
		reservation.setConfirmation_timestamp(new Date());
		reservationDao.updateReservation(reservation);
		
		// Creating the invoice
		Invoice invoice = new Invoice();
		invoice.setId_res(tracking_number);
		invoice.setIn_num(invoiceDao.generateInvoiceId());
		invoice.setInvoice_date(new Date());
		invoiceDao.addInvoice(invoice);
		
		// Generate the invoice and send it with an email
		try {
			generateInvoicePdf(invoice, reservation);
			sendEmailInvoice(reservation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Updating the numPendingReservations attribute
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		session.setAttribute("numPendingReservations", reservationDao.getOwnerPendingReservations(
				credentials.getId_actor()).size());
		
		// Creating an alert
		Alert alert = new Alert();
		alert.setPage_title("Reservation accepted");
		alert.setDescription("An invoice has been created and sent to the interested tenant jointly with"
				+ " your contact information. You can view it in the Reservations section.");
		alert.setButton_label("Return to reservations list");
		alert.setNext_url("/reservation/list.html");
		model.addAttribute("alert", alert);
		
		return "alert";
	}
	
	@RequestMapping("/reject/{tracking_number}")
	public String rejectReservation(HttpSession session, @PathVariable Integer tracking_number) {
		
		Reservation reservation = reservationDao.getReservation(tracking_number);
		Property property = propertyDao.getProperty(reservation.getId_property());
		// Checking if the user has been authenticated and he has permissions
		if(!authenticated(session) || !hasPermissions(session, new Object[] {property.getOwner()})) {
			session.setAttribute("nextUrl", "/reservation/accept/"+tracking_number+".html");
			return "redirect:/login.html";
		}
		
		// Making the corresponding period available again
		Period period = new Period();
		period.setId_prop(reservation.getId_property());
		period.setStart_date(reservation.getStart_date());
		period.setFinish_date(reservation.getFinish_date());
		periodDao.addPeriod(period);
		
		reservation.setStatus("rejected");
		reservationDao.updateReservation(reservation);
		
		return "redirect:/reservation/list.html";
	}
	
	private void generateInvoicePdf(Invoice invoice, Reservation reservation) throws Exception {
		
		Property property = propertyDao.getProperty(reservation.getId_property());
		Actor owner = actorDao.getActor(property.getOwner());
		
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		@SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, 
				new FileOutputStream(ImageController.APP_DIR_PATH+"\\pdf\\invoice_"+reservation.getTracking_number()+".pdf"));
		document.open();
		document.add(new Paragraph("EasyRent", FontFactory.getFont(FontFactory.HELVETICA, 
			    18, Font.BOLDITALIC, CMYKColor.BLACK)));
	
		document.add(new Paragraph("Invoice number: "+invoice.getIn_num(), FontFactory.getFont(FontFactory.HELVETICA, 
			    16, Font.BOLDITALIC, CMYKColor.BLACK)));
		
		PdfPTable t = new PdfPTable(2);
	    t.setSpacingBefore(25);
	    t.setSpacingAfter(25);
		
	    t.addCell("Property address: ");
		   
		t.addCell(property.getAddress());
	    
		t.addCell("Num. people: ");
		   
		t.addCell(""+reservation.getNum_people());
		
		t.addCell("Start date: ");
		   
		t.addCell(reservation.getStart_date().toString());
		
		t.addCell("Finish date: ");
		   
		t.addCell(reservation.getFinish_date().toString());
		   
		document.add(t);
		
		document.add(new Paragraph("Total amount: "+reservation.getTotal_amount()+"$", FontFactory.getFont(FontFactory.HELVETICA, 
			    16, Font.BOLD, CMYKColor.BLACK)));
		
		document.add(new Paragraph("Owner's contact information", FontFactory.getFont(FontFactory.HELVETICA, 
			    16, Font.UNDERLINE, CMYKColor.BLACK)));
		
		document.add(new Paragraph("Name: "+owner.getName()+" "+owner.getSurname(),
	    		FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, CMYKColor.BLACK)));
		
		document.add(new Paragraph("Phone number: "+owner.getPhone_number(),
	    		FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, CMYKColor.BLACK)));
		
		document.add(new Paragraph("Email: "+owner.getEmail(),
	    		FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, CMYKColor.BLACK)));
		
		document.add(new Paragraph("Invoice autogenerated by Easy Rent.",
	    		FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, CMYKColor.BLACK)));
		
		document.close();
	}
	
	private void sendEmailInvoice(Reservation reservation) throws Exception {
		Property property = propertyDao.getProperty(reservation.getId_property());
		Actor owner = actorDao.getActor(property.getOwner());
		Actor tenant = actorDao.getActor(reservation.getTenant());
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setFrom("easyrentadjos@gmail.com");
		helper.setTo(tenant.getEmail());
		helper.setSubject("Reservation invoice");
		helper.setText("<b>"+owner.getName()+" has accepted your reservation request of the property "
				+property.getTitle()+ ". This email contains an invoice with contact information. Have a nice renting!</b>", true);
		File file = new File(ImageController.APP_DIR_PATH+"\\pdf\\invoice_"+reservation.getTracking_number()+".pdf");
		helper.addAttachment("Invoice", file);
		
		mailSender.send(message);
	}
	
	@Override
	public boolean hasPermissions(HttpSession session, Object[] args) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		
		if(args[0] == null)
			return credentials.getRole().equals("owner") || credentials.getRole().equals("tenant");
		
		String id_owner = (String) args[0]; 
		return credentials.getId_actor().equals(id_owner);
	}
	
}
