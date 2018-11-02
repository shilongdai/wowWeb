package net.viperfish.auth.core;

import java.io.Serializable;
import java.util.Collection;
import javax.ejb.Remote;
import net.viperfish.auth.exceptions.UserExistsException;
import org.omg.PortableServer.ServantActivator;

@Remote
public interface UserManager extends Serializable {

	User registerUser(String email, String username, String password) throws UserExistsException;

	User getUser(String username);

	User getUser(Long id);

	Collection<User> getAllUsers();

	User updatePassword(String username, String password);

	User updatePassword(Long id, String password);

	void deleteUser(String username);

	void deleteUser(Long id);

	boolean contains(String username);

}
