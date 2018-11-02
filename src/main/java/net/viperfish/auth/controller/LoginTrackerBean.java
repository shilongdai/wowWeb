package net.viperfish.auth.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SessionScoped
@Named("loginStatus")
public class LoginTrackerBean implements Serializable {

	private static final long serialVersionUID = -6890858623741026318L;
	private Logger logger;

	@PostConstruct
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public boolean getLoginFailed() {
		if (FacesUtils.getSession().getAttribute("loginFailed") != null) {
			return (boolean) FacesUtils.getSession().getAttribute("loginFailed");
		}
		return false;
	}

	public void loginFailed() {
		FacesUtils.getSession().setAttribute("loginFailed", true);
		try {
			FacesUtils.sendRedirect("index.xhtml");
		} catch (IOException e) {
			logger
				.warn("Failed to redirect user back to login page after authentication failure", e);
		}
	}
}
