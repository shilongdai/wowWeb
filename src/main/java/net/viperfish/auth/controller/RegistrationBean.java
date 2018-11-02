package net.viperfish.auth.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import net.viperfish.auth.core.UserManager;
import net.viperfish.auth.exceptions.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("registrator")
@RequestScoped
@RunAs("auto")
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = 2179289867076243384L;
	private String username;
	private String password;
	@EJB
	private UserManager userManager;
	private Logger logger;

	@PostConstruct
	public void init() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void register() {
		try {
			logger.info("Registering user:" + username);
			userManager.registerUser("", username, password);
			FacesUtils.getRequest().login(username, password);
			FacesUtils.getSession().setAttribute("displayWelcome", true);
			FacesUtils.sendRedirect("index.xhtml");
		} catch (UserExistsException | IOException e) {
			// UI should already checked for this
			// Additionally, the login.xhtml exists
			logger.error("Assertion Error:", e);
			throw new RuntimeException(e);
		} catch (ServletException e) {
			logger.error("Failed to login user automatically:", e);
		}
	}
}
