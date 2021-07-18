package com.revature.exceptions;

/*
 * Very high level exception indicating any issue we have loading data from
 * plaid, this error allows us to unwind the stack and indicate that user
 * should try to process their request again
 * @author : Jack Walsh
 */

public class PlaidException extends Exception {

	private static final long serialVersionUID = -4978028340166299531L;

	public PlaidException() {
		super();
	}

	public PlaidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PlaidException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlaidException(String message) {
		super(message);
	}

	public PlaidException(Throwable cause) {
		super(cause);
	}

	
	
}
