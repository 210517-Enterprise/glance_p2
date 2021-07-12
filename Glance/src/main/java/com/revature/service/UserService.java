//package com.revature.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import com.revature.models.Account;
////Project Imports
//import com.revature.models.User;
//import com.revature.repositories.UserDAOInterface;
//import com.revature.util.APIAccessUtil;
//
////Other Imports
//
//
//@Service
//public class UserService {
//
//	/*
//	 * Executes methods that involve user updates and displays
//	 * 	Spring Bean
//	 * 
//	 * Author: Jack
//	 * 
//	 * 	Prospective methods:
//	 * 		- login
//	 * 		- update user credentials
//	 * 		- view accounts 
//	 * 
//	 */
//	
//	
//		/* DECLARE VARIABLES */
//	
//	//needs to be constructor initialized
//	//I dont think this needs to be "injected", simply needs to be passed
//	//in the getBeans method with args
//	private User user;
//	
//	//Can be autowired - all userDAO's should be the same
//	@Autowired
//	@Qualifier("UserDAOImpl")
//	private static UserDAOInterface userDAO;
//	
//	//connection Util supplies connections to the API
//	private static APIAccessUtil plaidUtil;
//	
//	
//	/* Constructor enforces dependency injection on user variable
//	 * 
//	 *  @param user user obj this service is based off of
//	 */
//	public UserService(User user) {
//		this.user = user;
//	}
//	
//	
//	
//	
//		/* DECLARE METHODS */
//	
//	
//	/* Attempts to log in a user by checking the entered email and password
//	 * against our saved quantites in the db, users table
//	 * 
//	 *  returns true if the email exists AND the password matches
//	 *  
//	 *  @param email string of users email in the db
//	 *  @param password string of users password in the db
//	 * 
//	 */
//	public static User login(String email, String password) {
//		
//		User u = userDAO.getUserByEmail(email);
//		
//		//check if u is null or if exception is thrown
//		
//		
//		//test if passwords match
//			// return u
//		
//		return null;
//	}
//	//END METHOD LOGIN
//	
//	
//	/* Attempts to create a new User Account with the provided information
//	 * 
//	 * 
//	 *  returns the user tuple that is now stored in the database
//	 *  
//	 *  @param info user type contains all signup info necessary to persist
//	 *  	user to the database
//	 */
//	public static User createNewUser(User info) {
//		
//		//Pass all relevant users signup info
//		
//		//May need to add extra paramater for plaid info
//		
//		//save User to db using DAO
//		
//		//GET user by id using its id and return this object
//		
//		return null;
//	
//	}
//	//END CREATE NEW USER METHOD 
//	
//	
//	/*  Attempts to load all account data for user from Plaid and store in RAM
//	 * 
//	 *  returns void
//	 */
//	public void loadAccounts() {
//		
//		//List<Account> accs = user1.getAccounts
//		
//		/*for(Account a : accs) {
//		 * 		plaidUtil.loadAccountData(a);
//		 * }
//		 */
//		
//	}
//	//END LOAD ACCOUNTS
//	
//	public Account addAccount(String itemid, String accesstoken) {
//		
//		
//		return null;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
////END USERSERVICE class