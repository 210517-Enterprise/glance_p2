 package com.revature.service;
 
 import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.stereotype.Service;
 
  //Project Imports
 import com.revature.entities.*;
 import com.revature.repositories.*;
 import com.revature.util.APIAccessUtil;
 
  //Other Imports
 
 
 @Service
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
 	
	 //FIXME Temporarily adding UserRepo as a parameter for the UserService class.
	 private UserRepository userRepo;
	 private AccountRepository accountRepo;
	 private GoalRepository goalRepo;
 	
 		/* DECLARE VARIABLES */
 	
		/*
		 * needs to be constructor initialized I dont think this needs to be "injected",
		 * simply needs to be passed in the getBeans method with args
		 */
 	private User user;
 	
// 	 Can be autowired - all userDAO's should be the same
 	@Autowired
 	@Qualifier("UserDAOImpl")
 	private static UserDAOInterface userDAO;
 	
 	//connection Util supplies connections to the API
 	private static APIAccessUtil plaidUtil;
 	
//FIXME Temporarly commenting this out in order to test the functionality of UserRepository. 	
// 	/* Constructor enforces dependency injection on user variable
// 	 * 
// 	 *  @param user user obj this service is based off of
// 	 */
// 	public UserService(User user) {
// 		this.user = user;
// 	}
 	
 	/**
 	 * FIXME, this is temporary implementation of a constructor that takes in all necessary repositories.
 	 * @param userRepo the user repository that contains all CRUD methods in addition to other queries provided by <code>UserRepository</code>
 	 * @param accountRepo the account repository that contains all CRUD methods in addition to other queries provided by <code>AccountRepository</code>
 	 * @param goalRepo
 	 */
 	@Autowired
 	public UserService(UserRepository userRepo, AccountRepository accountRepo, GoalRepository goalRepo) {
 		this.userRepo = userRepo;
 		this.accountRepo = accountRepo;
 		this.goalRepo = goalRepo;
 	}
 	
 	
 	
 	
 		/* DECLARE METHODS */
 	
 	
 	/* Attempts to log in a user by checking the entered email and password
 	 * against our saved quantites in the db, users table
 	 * 
 	 *  returns true if the email exists AND the password matches
 	 *  
 	 *  @param email string of users email in the db
 	 *  @param password string of users password in the db
 	 * 
 	 */
 	public static User login(String email, String password) {
 		
 		//User u = userDAO.getUserByEmail(email);
 		
 		 //check if u is null or if exception is thrown
 		
 		
 		// test if passwords match
 			  //return u;
 		
 		return null;
 	}
 	//END METHOD LOGIN
 	
 	
 	/* Attempts to create a new User Account with the provided information
 	 * 
 	 *  returns the user tuple that is now stored in the database
 	 *  
 	 *  @param info user type contains all signup info necessary to persist
 	 *  	user to the database
 	 */
 	public static User createNewUser(User info) {
 		
 		 //Pass all relevant users signup info
 		
 		 //May need to add extra paramater for plaid info
 		
 		 //save User to db using DAO
 		
 		//GET user by id using its id and return this object
 		
 		return null;
 	
 	}
 	 //END CREATE NEW USER METHOD 
 	
 	
 	/*  Attempts to load all account data for user from Plaid and store in RAM
 	 * 
 	 *  returns void
 	 */
 	public void loadAccounts() {
 		
 		 //List<Account> accs = user1.getAccounts
 		
 		/*for(Account a : accs) {
 		 * 		plaidUtil.loadAccountData(a);
 		 * }
 		 */
 		
 	}
 	 //END LOAD ACCOUNTS
 	
 	public Account addAccount(String itemid, String accesstoken) {
		 //get the account from Plaid
			// String returnInfo = plaidUtil.findAccount(accesstoken);
		
		 //build account type from returninfo
			 //Account = new Account(returnInfo);
		
		 //save account info with accountsDAO
 		
 		
		return null;
 	}
 	// END ADD ACCOUNT
 	
 	
// 	public String getAllAccountsAsJSON() {
// 		List<Account> accs = user.getAccounts();
// 		
// 		for(Account a : accs) {
// 			
// 		}
// 	}
 	
 	
 	
 	
 	
 	
 	
 	
 }
  //END USERSERVICE class

