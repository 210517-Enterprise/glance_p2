package com.revature.exceptions;

/*
 * Very high level exception indicating any issue we have loading data from
 * plaid, this error allows us to unwind the stack and indicate that user
 * should try to process their request again
 */

public class PlaidException extends Exception {

	/**
	 * 	Generated default serial id for serializable class
	 */
	private static final long serialVersionUID = -4978028340166299531L;

	public PlaidException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlaidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PlaidException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PlaidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PlaidException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
