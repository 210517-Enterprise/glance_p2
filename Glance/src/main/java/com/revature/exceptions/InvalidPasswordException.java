package com.revature.exceptions;

/*
 * Simple exception type thrown when a user enters an invalid password, this error is propogated
 * to the controller and handled by the front end
 */

public class InvalidPasswordException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPasswordException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public InvalidPasswordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
//END CLASS IllegalPasswordException
