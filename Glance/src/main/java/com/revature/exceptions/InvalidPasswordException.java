package com.revature.exceptions;

/*
 * Simple exception type thrown when a user enters an invalid password, this error is propagated
 * to the controller and handled by the front end
 * @author : Jack Walsh
 */

public class InvalidPasswordException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException() {
		super();
	}

	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordException(String s) {
		super(s);
	}

	public InvalidPasswordException(Throwable cause) {
		super(cause);
	}

	
}
//END CLASS IllegalPasswordException
