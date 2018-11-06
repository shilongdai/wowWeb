package net.viperfish.auth.controller;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import javax.servlet.ServletException;
import net.viperfish.auth.core.User;
import net.viperfish.auth.core.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("profile")
@SessionScoped
@RolesAllowed({"user", "administrator"})
public class UserProfileBean implements Serializable {

	private static final long serialVersionUID = -8543523491967061408L;
	@EJB
	private UserManager userManager;
	private User user;
	private Logger logger;

	public void init() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	public User getUser() {
		Principal userPrincipal = FacesUtils.getUserPrincipal();
		if (user == null) {
			user = userManager.getUser(userPrincipal.getName());
		}
		return user;
	}

	public String getPassword() {
		return "";
	}

	public void setPassword(String password) {
		try {
			if (password.isEmpty()) {
				FacesUtils.sendRedirect("user/userProfile.xhtml");
				return;
			}
			this.userManager.updatePassword(user.getId(), password);
		} catch (IOException e) {
			logger.warn("Failed to redirect user to userProfile.xhtml");
		}
	}

	public void logout() {
		try {
			FacesUtils.getRequest().logout();
			FacesUtils.getSession().invalidate();
			FacesUtils.sendRedirect("index.xhtml");
		} catch (ServletException e) {
			logger.warn("Failed to logout user:" + getUser().getUsername(), e);
		} catch (IOException e) {
			logger.warn("Failed to redirect user to index.xhtml", e);
		}
	}

	public boolean isDisplayWelcome() {
		Object shouldDisplay = FacesUtils.getSession()
			.getAttribute("displayWelcome");
		if (shouldDisplay == null) {
			return false;
		}
		boolean result = (boolean) shouldDisplay;
		return result;
	}

	public void welcomeDisplayed(PhaseEvent event) {
		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			FacesUtils.getSession().removeAttribute("displayWelcome");
		}
	}

}
