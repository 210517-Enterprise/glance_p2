package com.revature.exceptions;

/*
* Generic Exception to be thrown if we fail to insert an account because some
* inner unique data already exists in our database
*/

public class ExistingUserException extends IllegalArgumentException {

	/**
	 * Default generated ID for serializing 
	 */
	private static final long serialVersionUID = -3176273105004043914L;

	public ExistingUserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExistingUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExistingUserException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public ExistingUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


	
	
}
