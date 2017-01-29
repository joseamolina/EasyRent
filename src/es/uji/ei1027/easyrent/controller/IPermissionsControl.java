package es.uji.ei1027.easyrent.controller;

import javax.servlet.http.HttpSession;

public interface IPermissionsControl {

	public default boolean authenticated(HttpSession session) {
		return session.getAttribute("credentials") != null;
	}
	
	public boolean hasPermissions(HttpSession session, Object[] args);
	
}
