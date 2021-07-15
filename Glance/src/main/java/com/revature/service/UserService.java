 package com.revature.service;
 
 import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
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
	
	 //Does not need to be "injected" - user is a PARAMETER, not a dependency and is 
	 //thus inherently injected upon construction of user service
 	private User user;
 	
 	//Can be autowired - all userRepo's should be the same
 	@Autowired
 	private static UserRepository userRepo;
 	
 	@Autowired
 	private static AccountRepository accRepo;
 	
 	@Autowired
 	private static GoalRepository goalRepo;
 	
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
 	 * @param accRepo the account repository that contains all CRUD methods in addition to other queries provided by <code>accRepository</code>
 	 * @param goalRepo
 	 */
 	@Autowired
 	public UserService(UserRepository userRepo, AccountRepository accRepo, GoalRepository goalRepo) {
 		this.userRepo = userRepo;
 		this.accRepo = accRepo;
 		this.goalRepo = goalRepo;
 	}
 	
 	
 	
 	
 		/* DECLARE METHODS */
 	
 	
 	/* Attempts to log in a user by checking the entered email and password
 	 * against our saved quantites in the db, users table
 	 * 
 	 *  returns User if the email exists AND the password matches
 	 *  
 	 *  @param email string of users email in the db
 	 *  @param password string of users password in the db
 	 *  
 	 *  Throws NoSuchTuple and InvalidPassword Exceptions
 	 * 		- These will be handled by passing an error code to the front end
 	 */
 	public static User login(String email, String password) throws NoSuchTupleException, InvalidPasswordException {
 		
 		User u = userRepo.findUserByEmail(email);
 		
 		if(u != null) {
 			System.out.println("ATTEMPING TO LOG USER IN: ");
 	 		System.out.println("\t Entered Pass: " + password);
 	 		System.out.println("\t Hashed Pass: " + BCrypt.hashpw(password, BCrypt.gensalt()));
 	 		System.out.println("\t Pass in DB: " + u.getPassword());
 	 		System.out.println();
 		}
 		
 		
 		 //check if u is null or if exception is thrown
 		if(u == null) {
 			throw new NoSuchTupleException("No User found with this email.");
 		} if(u.getPassword().equals(BCrypt.hashpw(password, BCrypt.gensalt()))) { 
 			return u; 
 		} else {
 			throw new InvalidPasswordException("Password did not match user account on record.");
 		}
		  
 	}
 
 	/* Attempts to create a new User Account with the provided information
 	 * 
 	 *  returns JSON STRING of User tuple that is now stored in the database
 	 *  
 	 *  @param info user type contains all signup info necessary to persist
 	 *  	user to the database
 	 *  
 	 *  THROWS ExistingAccountException
 	 */
 	public static User createNewUser(User info) throws ExistingAccountException, IllegalArgumentException {
 		
 		 //Pass all relevant users signup info
 		
 		 //May need to add extra paramater for plaid info
 		 User saved = null;
 		try {
 	 		 //save User to db using DAO
 			saved = userRepo.save(info);
 		} catch(IllegalArgumentException e) {
 			//Any number of errors this could fail to save, we are concerned with:
 			if(userRepo.findUserByEmail(info.getEmail()) != null) {
 				throw new ExistingAccountException("Problem creating account: A user already exists with this email");
 			} else {
 				throw new IllegalArgumentException("Unkown problem creating account");
 			}
 			
 		}
 		
 		return saved;
 	
 	}
 	 //END CREATE NEW USER METHOD 
 	
 	
 	
 	/*  Attempts to load all account data for user from Plaid and store in RAM
 	 * 
 	 *  returns void
 	 *  
 	 *  THROWS plaidException if there is an error with plaid
 	 */
 	public void loadAccounts() {
 		
 		 List<Account> accs = user.getAccounts();
 		
 		for(Account a : accs) {
 		  		//plaidUtil.loadAccountData(a);
 		  }
 		 	
 	}
 	 //END LOAD ACCOUNTS
 	
 	
 	/**
 	 * @param plaidAccountId - some personal identifier specific to the user
 	 * @param accesstoken	- 	the bank specific accesstoken the user has acquired from plaid
 	 * 
 	 * @return JSON String of new Account added
 	 */
 	public Account addAccount(String plaidAccountId, String accesstoken) {
		 //get the account from Plaid
			// String returnInfo = plaidUtil.findAccount(accesstoken);
		
 		//Will likely have to split return info into array since access tokens are
 		//specific to BANKS, not independent accounts
 			//String[] accs = returnInfo.split();
 		
		 //build account type from returninfo
			 //Account = new Account(returnInfo);
		
		 //save account info with accountsDAO
 		
 		
		return null;
 	}
 	// END ADD ACCOUNT
 	
 	
 	public String getAllAccountsAsJSON() 
 	{
 		List<Account> accs = user.getAccounts();
 		StringBuilder sb = new StringBuilder();
 		
 		sb.append("[");
 		for(Account a : accs) {
 			sb.append(a.stringAsJSON());
 		}
 		
 		return sb.toString();
 	}
 	//END GET ALL ACCOUNTS

 	
 	public String getAccountAsJSON(int internalID) {
 		//return accRepo.findById(internalID).stringAsJSON();
 		return null;
 	}// END GETACCOUNTASJSON
 	
 	
 	
 	
 	
 	
 	
 }
  //END USERSERVICE class

