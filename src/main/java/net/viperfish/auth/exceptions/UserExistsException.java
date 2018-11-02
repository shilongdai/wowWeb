package net.viperfish.auth.exceptions;

public class UserExistsException extends Exception {

	private static final long serialVersionUID = -6734401254848329347L;

	public UserExistsException() {
	}

	public UserExistsException(String message) {
		super(message);
	}

	public UserExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserExistsException(Throwable cause) {
		super(cause);
	}
}
