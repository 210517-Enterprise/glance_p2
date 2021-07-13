package com.revature.exceptions;

public class ResourceNotFoundException extends GlanceException{
	
	private static final long serialVersionUID = 5220673095319043199L;

	public ResourceNotFoundException(){
		super("No resource(s) found.");
	}
}
