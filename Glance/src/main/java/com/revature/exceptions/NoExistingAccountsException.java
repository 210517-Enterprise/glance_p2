package com.revature.exceptions;


/*
 * Generic exception thrown when Plaid does not return any accounts tethered to the 
 * Access token and account id. This exception may also be thrown if the accounts
 * are already added. 
 */

public class NoExistingAccountsException extends Exception {

	public NoExistingAccountsException() {
		// TODO Auto-generated constructor stub
	}

	public NoExistingAccountsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoExistingAccountsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoExistingAccountsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoExistingAccountsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
