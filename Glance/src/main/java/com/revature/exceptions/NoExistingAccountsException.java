package com.revature.exceptions;


/*
 * Generic exception thrown when Plaid does not return any accounts tethered to the 
 * Access token and account id. This exception may also be thrown if the accounts
 * are already added.
 * @author : Jack Walsh 
 */

public class NoExistingAccountsException extends Exception {

	private static final long serialVersionUID = -979587526384959673L;

	public NoExistingAccountsException() {
	}

	public NoExistingAccountsException(String message) {
		super(message);
	}

	public NoExistingAccountsException(Throwable cause) {
		super(cause);
	}

	public NoExistingAccountsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoExistingAccountsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
