package net.viperfish.auth.controller;

import java.io.IOException;
import java.security.Principal;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class FacesUtils {
	private FacesUtils() {

	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Principal getUserPrincipal() {
		return getRequest().getUserPrincipal();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static void sendRedirect(String path) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(path);
	}
}
