 package com.revature.service;
 
 import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsGetResponse;
import com.plaid.client.model.Transaction;
import com.plaid.client.model.TransactionsGetResponse;
import com.revature.controller.PlaidController;
//Project Imports
 import com.revature.entities.*;
 import com.revature.repositories.*;
 import com.revature.exceptions.*;
 
  //Other Imports
 
 
 @Service
 @Transactional
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
 	
 	@Autowired
 	private UserRepository userRepo;
 	
 	@Autowired
 	private AccountRepository accRepo;
 	
 	@Autowired
 	private GoalRepository goalRepo;
 	
 	@Autowired
 	private PlaidController plaidUtil;
 	
 	 	
 		/* DECLARE METHODS */
 	public UserService() {
 		
 	}
 	
 	

 	/**
 	 * @param email string of users email in the db
 	 * @param password string of users password in the db
 	 * 
 	 * @return user to be logged in
 	 * 
 	 * @throws NoSuchTupleException	- if email is not found in DB
 	 * @throws InvalidPasswordException - if password does not match email for found account 
 	 */
	public User login(String email, String password) throws NoSuchTupleException, InvalidPasswordException {
	    
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
	    } if(BCrypt.checkpw(password, u.getPassword())) {
	        //set internal user to logged in value
	        System.out.println("Login Successful, returning user");
	        return u; 
	    } else {
	        throw new InvalidPasswordException("Password did not match user account on record.");
	    }
	     
	}
//END LOGIN METHOD

 
 	/* Attempts to create a new User Account with the provided information
 	 * 
 	 *  returns JSON STRING of User tuple that is now stored in the database
 	 *  
 	 *  @param info user type contains all signup info necessary to persist
 	 *  	user to the database
 	 *  
 	 *  THROWS ExistingAccountException
 	 */
 	public User createNewUser(User info) throws ExistingAccountException, IllegalArgumentException {
 		
 		 //Pass all relevant users signup info
 		
 		 //May need to add extra paramater for Plaid info
 		 User saved = null;
 		 info.setPassword(BCrypt.hashpw(info.getPassword(), BCrypt.gensalt()));
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
 	

 	
 	/**
 	 * @param PlaidAccountId - some personal identifier specific to the user
 	 * @param accesstoken	- 	the bank specific accesstoken the user has acquired from Plaid
 	 * 
 	 * @return List<Account> of new Accounts added (this contains only data stored in our DB, no balances, transactions etc.)
 	 * @throws NoExistingAccountsException, NoSuchTupleException, PlaidException,
 	 */
 	public List<Account> addAccounts(int internalUserID, String accesstoken) throws NoSuchTupleException, PlaidException, NoExistingAccountsException {
		 //get the account from Plaid
 		AccountsGetResponse returnInfo = null;
			try {
				 returnInfo = plaidUtil.getAccounts(accesstoken);
			} catch (IOException e) {
				throw new PlaidException("Problem reading account data from Plaid with this accessToken");
			}
		
 		//Will likely have to split return info into list since access tokens are
 		//specific to BANKS, not independent accounts
		List<AccountBase> accs = returnInfo.getAccounts();
		List<Account> loadedAccounts = new ArrayList<>();
		
		//Attempt to find the user with the given id.
		
		User u = null;
		try {
			u = userRepo.findUserById(internalUserID);
			if(u == null) {
				throw new NoSuchTupleException("Failed to find user with this internal ID");
			}
		} catch (NoSuchTupleException e) {
			//rethrow exception as unknown user will have no accounts
			throw e;
		}
			

		 //build account type from returninfo
			 for (AccountBase accountBase : accs) {
				
				 try {
					
					//save account info with accountsDAO
					Account a = new Account(accesstoken, accountBase.getAccountId(), u);
					Account savedAcc = accRepo.save(a);
					
					if(savedAcc == null) {
						throw new IllegalArgumentException();
					}
					
					loadedAccounts.add(savedAcc);
				} catch (IllegalArgumentException e) {
					//Code will continue, try to add next accounts
				}
				
			}
			 //END FOR
			 
			 //Need to check if no new accounts were found - especially if the user already tried to add
			 //these accounts and attempting to save them to the db would violate unique constraints.
			 if(loadedAccounts.isEmpty()) {
				 throw new NoExistingAccountsException("No new accounts associated with this access token and account ID");
			 }
		
		return loadedAccounts;
 	}
 	// END ADD ACCOUNT
 	
 	/*  Attempts to load all account data for user from Plaid and store in RAM
 	 * 
 	 *  returns void
 	 *  
 	 *  THROWS PlaidException if there is an error with Plaid
 	 */
 	public List<String> getAllAccounts(int internalUserID) throws NoSuchTupleException, PlaidException {	
 		 List<Account> accs = accRepo.findAccountByUserId(internalUserID);
 		 List<String> accData = new ArrayList<>();
 		 
 		for(Account a : accs) {
 		  		try {
					String accInfo = getAccount(a.getId());
					accData.add(accInfo);
				} catch (NoSuchTupleException e) {
					//Continue to next account
				}
 		  }
 		//END FOR
		return accData;	 
 	}
 	//END GET ALL ACCOUNTS

 	
 	
 	
 	public String getAccount(int internalID) throws NoSuchTupleException, PlaidException {
 		
 		Optional<Account> a = accRepo.findById(internalID);
 		
 		if(a.isPresent()) {
 			try {
				AccountsGetResponse returnInfo = plaidUtil.getAccounts(a.get().getPlaidItem());
				
	 			for (AccountBase ab : returnInfo.getAccounts()) 
	 			{
	 				
	 				//What we call PlaidKey is what they call their unique Plaid ID
	 				//but the value on their end is subject to change so this may have to be modified
	 				//so we know how to match their account with OUR internall account instance
					if(ab.getAccountId().equals(a.get().getPlaidKey())) {
						System.out.println("------------------------------------------\n");
						System.out.println("Info from Plaid on account: \n");
						ab.toString();
						System.out.println("------------------------------------------\n");
						return ab.toString();
					}
				}
	 			//END FOR, exception thrown if we reach here
	 			
			} catch (IOException e) {
				throw new PlaidException("Problem reading account data from Plaid with this accessToken");
			}
 			
 		}
 		
 		//If we reach here the account was not present or has no matching plaid key
 		throw new NoSuchTupleException("Could not find account with ID: " + internalID);
 		
 	}
 	// END GETACCOUNT
 	
 	
 	
 	
 	
 	/**
 	 * @param internalID - internal ID of account that we desire transactions for
 	 * @return - List, in JSON form, of recent transactions associated with this account
 	 */
 	public List<String> getTransactionsForAccount(int internalID) throws NoSuchTupleException, PlaidException 
 	{
 		/*
 		 * Return transactions for a particular account
 		 * 
 		 */
 		
 		try {
			TransactionsGetResponse transactions = plaidUtil.getTransactions(accRepo.findPlaidItemById(internalID));
			
			List<Transaction> temp = transactions.getTransactions();
			List<String> transactionsList = new ArrayList<>();
			
			for(Transaction t : temp) {
				String tempPlaid = accRepo.findPlaidKeyById(internalID);
				if (accRepo.findPlaidKeyById(internalID).equals(t.getAccountId())) {
					transactionsList.add(t.toString());
				}
			}
			
			return transactionsList;
		} catch (IOException e) {
			throw new PlaidException("Problem reading account data from Plaid with this accessToken");
		}
 		
 	}
 	
 	
 	
 	
 }
  //END USERSERVICE class

