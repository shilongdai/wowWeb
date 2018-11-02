package net.viperfish.auth.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.viperfish.auth.exceptions.UserExistsException;
import net.viperfish.auth.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton(name = "jpaUserManager", description = "A UserManager implemented with JPA")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@DeclareRoles({"auto", "administrator"})
@RolesAllowed({"auto", "administrator"})
public class JPAUserManager implements UserManager {

	private static final long serialVersionUID = -56742233999185134L;

	@PersistenceContext(unitName = "trinity")
	private EntityManager userEntityManager;
	private Logger logger;

	@PostConstruct
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	@Override
	@PermitAll
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public User registerUser(String email, String username, String password)
		throws UserExistsException {
		System.out.println("Registering User");
		User existing = getUser(username);
		if (existing != null) {
			logger.debug("User:" + username + " Already Exists");
			throw new UserExistsException(username);
		}
		logger.info("Creating User:" + username);
		User toRegister = new User();
		toRegister.setUsername(username);
		toRegister.setEmail(email);
		String hashed = hashPassword(username, password);
		toRegister.setHashedPassword(hashed);
		toRegister.registerRole(new UserRole(username, "user"));
		userEntityManager.persist(toRegister);
		logger.info("Persisting:" + toRegister);
		userEntityManager.flush();
		return toRegister;
	}

	@Override
	@RolesAllowed({"auto", "administrator"})
	public Collection<User> getAllUsers() {
		Query userQuery = userEntityManager.createNamedQuery("findAll");
		return userQuery.getResultList();
	}

	@Override
	@RolesAllowed({"auto", "administrator", "user"})
	public User getUser(String username) {
		Query userQuery = userEntityManager.createNamedQuery("findByUsername")
			.setParameter("name", username);
		try {
			User user = (User) userQuery.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	@RolesAllowed({"auto", "administrator", "user"})
	public User getUser(Long id) {
		return userEntityManager.find(User.class, id);
	}

	@Override
	@RolesAllowed({"administrator", "auto"})
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public User updatePassword(String username, String password) {
		User user = getUser(username);
		if (user == null) {
			throw new UserNotFoundException(username);
		}
		user.setHashedPassword(hashPassword(username, password));
		userEntityManager.persist(user);
		return user;
	}

	@Override
	@RolesAllowed({"auto", "administrator"})
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public User updatePassword(Long id, String password) {
		User user = userEntityManager.find(User.class, id);
		if (user == null) {
			throw new UserNotFoundException(id.toString());
		}
		user.setHashedPassword(hashPassword(user.getUsername(), password));
		userEntityManager.persist(user);
		return user;
	}

	@Override
	@RolesAllowed({"auto", "administrator"})
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void deleteUser(String username) {
		Query deleteQuery = userEntityManager.createNamedQuery("deleteByUsername")
			.setParameter("name", username);
		deleteQuery.executeUpdate();
	}

	@Override
	@RolesAllowed({"auto", "administrator"})
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void deleteUser(Long id) {
		User toDelete = userEntityManager.find(User.class, id);
		if (toDelete != null) {
			userEntityManager.remove(toDelete);
		}
	}

	@Override
	@PermitAll
	public boolean contains(String username) {
		User existing = getUser(username);
		return existing != null;
	}

	private String hashPassword(String username, String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			String usernameUpper = username.toUpperCase();
			String passwordUpper = password.toUpperCase();
			byte[] usernameBytes = usernameUpper.getBytes(StandardCharsets.UTF_8);
			byte[] passwordBytes = passwordUpper.getBytes(StandardCharsets.UTF_8);
			digest.update(usernameBytes);
			digest.update(":".getBytes(StandardCharsets.UTF_8));
			digest.update(passwordBytes);
			byte[] hashed = digest.digest();
			StringBuilder result = new StringBuilder();
			for (byte b : hashed) {
				result.append(String.format("%02X", b));
			}
			return result.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("SHA1 is not supported by this Java implementation");
		}
	}

}
