package com.revature.repositories;

import com.revature.entities.User;


public interface UserDAOInterface {

	/*
	 * Interface for updating and accessing user information in our db
	 * 
	 * Author: Jack
	 * 
	 * Prospective methods:
	 * 		- view account info with id=x
	 * 		- add account
	 * 		- remove account
	 * 		- Set goal 
	 */
	
	
	public User getUserByEmail(String email);
}
