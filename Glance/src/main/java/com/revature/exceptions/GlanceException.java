package com.revature.exceptions;

/*
 * General Exception for unspecified errors
 * @author : Jack Walsh
 */

public class GlanceException extends RuntimeException{
	
	private static final long serialVersionUID = 8512712143164755580L;

	public GlanceException(Throwable e) {
		super("An unspecified exception was thrown, see the logs for more information.", e);
	}
	
	public GlanceException(String message) {
		super(message);
	}
}
