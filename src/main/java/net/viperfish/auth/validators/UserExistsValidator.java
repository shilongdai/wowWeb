package net.viperfish.auth.validators;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import net.viperfish.auth.core.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("userExistsValidator")
@RequestScoped
public class UserExistsValidator implements Validator {

	@EJB
	private UserManager userManager;
	private Logger logger;

	@PostConstruct
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
		throws ValidatorException {
		String username = (String) value;
		logger.info("Checking username in database");
		logger.info("Validating {}", username);
		if (userManager.contains(username)) {
			logger.info("User {} already exists!", username);
			FacesMessage message = new FacesMessage("User " + username + " Already Exists");
			throw new ValidatorException(message);
		}
	}
}
