package net.viperfish.auth.exceptions;

public class UserNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = 8652510304432346160L;

	public UserNotFoundException() {
	}

	public UserNotFoundException(String s) {
		super(s);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
