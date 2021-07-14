package com.revature.exceptions;

/*
 * Generic Exception to be thrown if we fail to insert an account because some
 * inner unique data already exists in our database
 */

public class ExistingAccountException extends IllegalArgumentException {

	/**
	 * Default Serial ID generated for us by Java
	 */
	private static final long serialVersionUID = 1L;

	
	/* Generated Constructors from superclass */
	public ExistingAccountException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExistingAccountException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExistingAccountException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public ExistingAccountException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
	
}
