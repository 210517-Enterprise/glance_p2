package com.revature.exceptions;
import java.sql.SQLException;

/**
*	Exception indicates that no tuple was found matching the id, email, password etc of the user
*	@author : Jack Walsh
*/

public class NoSuchTupleException extends SQLException {

	private static final long serialVersionUID = 3744674872313813559L;

	public NoSuchTupleException() {
		super();
	}

	public NoSuchTupleException(String arg0) {
		super(arg0);
	}

	public NoSuchTupleException(Throwable arg0) {
		super(arg0);
	}

	public NoSuchTupleException(String arg0, String arg1) {
		super(arg0, arg1);
	}

	public NoSuchTupleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoSuchTupleException(String arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	public NoSuchTupleException(String arg0, String arg1, Throwable arg2) {
		super(arg0, arg1, arg2);
	}

	public NoSuchTupleException(String arg0, String arg1, int arg2, Throwable arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
