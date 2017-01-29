package es.uji.ei1027.easyrent.domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
	private String username;
	private String password;
	
	public SmtpAuthenticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
	
}
