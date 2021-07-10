package com.revature.service;

//Project Imports
import com.revature.models.User;
import com.revature.repositories.UserDAOInterface;

//Other Imports

public class UserService {

	/*
	 * Executes methods that involve user updates and displays
	 * 	Spring Bean
	 * 
	 * Author: Jack
	 * 
	 * 	Prospective methods:
	 * 		- login
	 * 		- update user credentials
	 * 		- view accounts 
	 * 
	 */
	
	User user;
	UserDAOInterface userDAO;
	
	/* Attempts to log in a user by checking the entered email and password
	 * against our saved quantites in the db, users table
	 * 
	 *  returns true if the email exists AND the password matches
	 *  
	 *  @param email string of users email in the db
	 *  @param password string of users password in the db
	 * 
	 */
	public boolean login(String email, String password) {
		
		User u = userDAO.getUserByEmail(email);
		
		//check if u is null or if exception is thrown
		
		
		//test if passwords match
		
		return false;
	}
	//END METHOD LOGIN
	
	
	
	
	
	
}
//END USERSERVICE class