package com.revature.exceptions;


/**
 * Exception thrown when a front end resource
 * like a webpage was unable to be found
 * @author : Jack Walsh
 */


public class ResourceNotFoundException extends GlanceException{
	
	private static final long serialVersionUID = 5220673095319043199L;

	public ResourceNotFoundException(){
		super("No resource(s) found.");
	}
}
