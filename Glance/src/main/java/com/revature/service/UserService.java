 package com.revature.service;
 
 //Language and dependency imports
 import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Project Imports
import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsGetResponse;
import com.plaid.client.model.Transaction;
import com.plaid.client.model.TransactionsGetResponse;
import com.revature.controller.PlaidController;
import com.revature.entities.*;
import com.revature.repositories.*;
import com.revature.exceptions.*;
 
  //Other Imports
 
 
/**
 * Provides service control for user objects collecting functionality in a single point for ease of use and transaction control.
 * @author Primary Authora: Jack Walsh and Kyle Castillo. Secondary Author: James Butler(autowiring and naming consistency only)
 *
 */
 @Service
 @Transactional
 public class UserService {
  	
 
	 /* DECLARE and AUTOWIRE necessary dependencies */
 	
 	@Autowired
 	private UserRepository userRepo;
 	
 	@Autowired
 	private AccountRepository accRepo;
 	
 	/*
 	 * The goal repository is necessary for future functional. Uncomment these lines when implemented. 
 	@Autowired
 	private GoalRepository goalRepo;
 	*/
 	
 	@Autowired
 	private PlaidController plaidUtil;
 	
 	 	
 		/* DECLARE METHODS */
 	public UserService() {
 		
 	}
 	
 	

 	/**
 	 * Verifies the login credentials of a login attempt against registered users and returns success or failure.
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
 	 *  
 	 *  @param info user type contains all signup info necessary to persist
 	 *  	user to the database
 	 *  
 	 *  @return The user that is now stored in the database.
 	 *  
 	 *  @throws ExistingAccountException Thrown when the user already exists in the database
 	 *  @throws IllegalArgumentException Thrown for any other problem with the input. 
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
 	
 	/**
 	 * Adds a users accounts that were found by Plaid LINK to the database and properly associates them with the user for later retrieval.
 	 * @param internalUserID The autogenerated userId within the Glance database
 	 * @param accesstoken the accesstoken return by LINK that will be consumed by the Plaid API to obtain accounts.
 	 * @return A list of type List<Account> of accounts that were found and added.
 	 * @throws NoSuchTupleException Thrown if a user cannot be found.
 	 * @throws PlaidException Thrown if there is an error in the Plaid API functionality (likely caused by an invalid token).
 	 * @throws NoExistingAccountsException Thrown if no accounts are found/returned for an access token.
 	 */
 	public List<Account> addAccounts(int internalUserID, String accesstoken) throws NoSuchTupleException, PlaidException, NoExistingAccountsException {
		 //get the account from Plaid
 		AccountsGetResponse returnInfo = null;
			try {
				 returnInfo = plaidUtil.getAccounts(accesstoken);
				 System.out.println("\n RETURN FROM PLAID WITH: " + returnInfo);
				 System.out.println("\n\n------------------------");
			} catch (IOException e) {
				throw new PlaidException("Problem reading account data from Plaid with this accessToken");
			}
		
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
					
					//save account info
					Account a = new Account(accesstoken, accountBase.getAccountId(), u);
					Account savedAcc = accRepo.save(a);
					
					//System.out.println("\t--Saved Account:");
					if(savedAcc == null) {
						throw new IllegalArgumentException();
					}
					
					loadedAccounts.add(savedAcc);
				} catch (IllegalArgumentException e) {
					//Code will continue, try to add next accounts
				}
				
			}
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
 	
 	/**
 	 * Returns a list of type List<String> of all accounts associated with a particular user. 
 	 * @param internalUserID The user's id as stored within the Glance database.
 	 * @return A list of accounts where each entry is a String of account information.
 	 * @throws NoSuchTupleException Thrown if an account's information cannot be found.
 	 * @throws PlaidException Thrown if there is an error within the Plaid API
 	 */
 	public List<String> getAllAccounts(int internalUserID) throws NoSuchTupleException, PlaidException {	
 		 List<Account> accs = accRepo.findAccountByUserId(internalUserID);
 		 List<String> accData = new ArrayList<>();
 		 
 		for(Account a : accs) {
 		  		try {
					String accInfo = getAccount(a.getPlaidKey());
					accData.add(accInfo);
				} catch (NoSuchTupleException e) {
					//Continue to next account
				}
 		  }
 		//END FOR
		return accData;	 
 	}
 	//END GET ALL ACCOUNTS

 	
 	
 	/**
 	 * Method to fetch information for a singular account. 
 	 * @param plaidID The user's Id within the Glance database.
 	 * @return A String containing the account information.
 	 * @throws NoSuchTupleException Thrown if an account cannot be found.
 	 * @throws PlaidException Thrown for any problems within the Plaid API
 	 */
 	public String getAccount(String plaidID) throws NoSuchTupleException, PlaidException {
 		
 		Account a = accRepo.findAccountByPlaidKey(plaidID);
 		
 		if(a != null) {
 			try {
				AccountsGetResponse returnInfo = plaidUtil.getAccounts(a.getPlaidItem());
				
	 			for (AccountBase ab : returnInfo.getAccounts()) 
	 			{
	 				
					if(ab.getAccountId().equals(plaidID)) {
						//System.out.println("------------------------------------------\n");
						//System.out.println("Info from Plaid on account: \n");
						//System.out.println("------------------------------------------\n");
						return ab.toString();
					}
				}
	 			//END FOR, exception thrown if we reach here
	 			
			} catch (IOException e) {
				throw new PlaidException("Problem reading account data from Plaid with this accessToken");
			}
 			
 		}
 		
 		//If we reach here the account was not present or has no matching plaid key
 		throw new NoSuchTupleException("Could not find account with ID: " + plaidID);
 		
 	}
 	// END GETACCOUNT
 	
 	
 	
 	
 	/**
 	 * Obtains the transaction history for an account. 
 	 * This is to be used for future functionality and should not be used within the current release.
 	 * @param internalID The account's id within the Glance database.
 	 * @return A list of type List<String> where each entry is a string representing a single transaction.
 	 * @throws NoSuchTupleException Thrown if no transaction can be found.
 	 * @throws PlaidException Thrown for all errors within the Plaid Api.
 	 */
 	public List<String> getTransactionsForAccount(String plaidID) throws NoSuchTupleException, PlaidException 
 	{
 		
 		try {
			TransactionsGetResponse transactions = plaidUtil.getTransactions(
					accRepo.findAccountByPlaidKey(plaidID).getPlaidItem());
			
			List<Transaction> temp = transactions.getTransactions();
			List<String> transactionsList = new ArrayList<>();
			
			for(Transaction t : temp) {
				if (plaidID.equals(t.getAccountId())) {
					transactionsList.add(t.toString());
				}
			}
			
			return transactionsList;
		} catch (IOException e) {
			throw new PlaidException("Problem reading account data from Plaid with this accessToken");
		} catch (NullPointerException e) {
			throw new NoSuchTupleException("No account exists with this plaidID");
		}
 		
 	}
 	
 	
 	
 	
 }
  //END USERSERVICE class

