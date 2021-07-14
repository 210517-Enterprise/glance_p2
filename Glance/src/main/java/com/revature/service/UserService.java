 package com.revature.service;
 
 import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.stereotype.Service;
 
  //Project Imports
 import com.revature.entities.*;
 import com.revature.repositories.*;
 import com.revature.exceptions.*;
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
 	
 	
 		/* DECLARE VARIABLES */
 	
		/*
		 * needs to be constructor initialized I dont think this needs to be "injected",
		 * simply needs to be passed in the getBeans method with args
		 */
	
	
 	private User user;
 	
// 	 Can be autowired - all userDAO's should be the same
 	@Autowired
 	@Qualifier("UserDAOImpl")
 	private static UserDAOImpl userDAO;
 	
 	@Autowired
 	@Qualifier("AccDAOImpl")
 	private static AccountDAOImpl accDAO;
 	
 	//connection Util supplies connections to the API
 	private static APIAccessUtil plaidUtil;
 	
 	
 	/* Constructor enforces dependency injection on user variable
 	 * 
 	 *  @param user user obj this service is based off of
 	 */
 	public UserService(User user) {
 		this.user = user;
 	}
 	
 	
 	
 	
 		/* DECLARE METHODS */
 	
 	
 	/* Attempts to log in a user by checking the entered email and password
 	 * against our saved quantites in the db, users table
 	 * 
 	 *  returns JSON STRING of User if the email exists AND the password matches
 	 *  
 	 *  @param email string of users email in the db
 	 *  @param password string of users password in the db
 	 *  
 	 *  Throws NoSuchTuple and InvalidPassword Exceptions
 	 * 		- These will be handled by passing an error code to the front end
 	 */
 	public static String login(String email, String password) throws NoSuchTupleException {
 		
 		User u = userDAO.getUserByEmail(email);
 		
 		 //check if u is null or if exception is thrown
 		if(u == null) {
 			throw new NoSuchTupleException("No tuple with this email in DB");
 		}
 		
 		// test if passwords match - need an encrypting dependency
		/*
		 * if(u.getPassword().equals(Spring.hashPassword(password))) { return u; }
		 * throw new InvalidPasswordException("Password did not match account");
		 */	
 		
 		return null;
 	}
 	//END METHOD LOGIN
 	
 	
 	/* Attempts to create a new User Account with the provided information
 	 * 
 	 *  returns JSON STRING of User tuple that is now stored in the database
 	 *  
 	 *  @param info user type contains all signup info necessary to persist
 	 *  	user to the database
 	 *  
 	 *  THROWS ExistingAccountException
 	 */
 	public static String createNewUser(User info) {
 		
 		 //Pass all relevant users signup info
 		
 		 //May need to add extra paramater for plaid info
 		
 		 //save User to db using DAO
 		
 		/*
 		 
 		try {
 			//int savedID = userDAO.save(info);
 		} catch(Exception e) {
 			//Any number of errors this could fail to save, we are concerned with:
 				// - EmailAlreadyExistsException
 				//Throw new ExistingAccountException("Account already exists with these values");
 		}
 		*/
 		
 		//GET user by id using its id and return this object
 			//return userDAO.getByID(savedID);
 		return null;
 	
 	}
 	 //END CREATE NEW USER METHOD 
 	
 	
 	
 	/*  Attempts to load all account data for user from Plaid and store in RAM
 	 * 
 	 *  returns void
 	 *  
 	 *  THROWS plaidException if there is an error with plaid
 	 */
 	public void loadAccounts() {
 		
 		 //List<Account> accs = user.getAllAccounts();
 		
 		/*for(Account a : accs) {
 		 * 		plaidUtil.loadAccountData(a);
 		 * }
 		 */
 		
 	}
 	 //END LOAD ACCOUNTS
 	
 	public Account addAccount(String plaidAccountId, String accesstoken) {
		 //get the account from Plaid
			// String returnInfo = plaidUtil.findAccount(accesstoken);
		
		 //build account type from returninfo
			 //Account = new Account(returnInfo);
		
		 //save account info with accountsDAO
 		
 		
		return null;
 	}
 	// END ADD ACCOUNT
 	
 	
 	public String getAllAccountsAsJSON() 
 	{
 		/*List<Account> accs = user.getAllAccounts();
 		StringBuilder sb = new StringBuilder();
 		
 		sb.append("[");
 		for(Account a : accs) {
 			sb.append(a.stringAsJSON());
 		}
 		*/
 		return null;
 	}
 	//END GET ALL ACCOUNTS
 	
 	public String getAccountAsJSON(int internalID) {
 		//return accDAO.findById(internalID).stringAsJSON();
 		return null;
 	}// END GETACCOUNTASJSON
 	
 	
 	
 	
 	
 	
 	
 }
  //END USERSERVICE class

