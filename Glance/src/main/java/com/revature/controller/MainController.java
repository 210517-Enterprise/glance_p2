package com.revature.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//Spring Web Includes
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Account;
//Glance Project Includes
import com.revature.entities.User;
import com.revature.exceptions.ExistingAccountException;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.NoExistingAccountsException;
import com.revature.exceptions.NoSuchTupleException;
import com.revature.exceptions.PlaidException;
import com.revature.service.UserService;


@RestController
public class MainController {

	
	/*
	 * Implemented by Kyle
	 * 
	 * Will direct resources to front-end for users
	 */
	
	private final UserService userService;
	
	@Autowired
	public MainController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * The login in method for the Glance application
	 * @param user the user attempting to log in. In this case the only two values that the user has is <code>email</code> and <code>password</code>
	 * @return a ResponseEntity either containing an HttpStatus code of OK or an HttpStatus code of NotFound. Additionally passes back the user if the email and password exist in the DB.
	 */
	@PostMapping(value="/login")
	public ResponseEntity<User> loginUser(User user){
		System.out.println(user);
		try {
			User foundUser = userService.login(user.getEmail(), user.getPassword());
			return ResponseEntity.ok(foundUser);
		} catch (InvalidPasswordException | NoSuchTupleException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	/**
	 * The registration method for the Glance application
	 * @param user the user that is attempting to be registered to the database.
	 * @return a ResponseEntity containing a status code 200 or a status code resulting in an error. If the status code is 200 it will also return the user registered.
	 */
	@PostMapping(value="/register")
	public ResponseEntity<User> registerUser (User user) {
		
		
		try {
			User createdUser = userService.createNewUser(user);
			return ResponseEntity.ok(createdUser);
		} catch (ExistingAccountException e) {
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	/**
	 * Adds all plaid accounts to the user account.
	 * @param id FIXME there needs to be an integer id to add the accounts to the user with the id of id.
	 * @param accessToken FIXME there needs to be a param to have the user's access token
	 * @return A status code of either 200 or no conent if it fails.
	 */
	@GetMapping(value="/addAccount")

	public @ResponseBody ResponseEntity<Account> addAccount(@RequestParam String accessToken) {
		//String accessToken = "access-sandbox-bd815122-d735-41bf-8119-08cdab46099d";
		int id = 1;
		System.out.println(accessToken + " " + id);
		

		try {
			List<Account> newAccounts = userService.addAccounts(id, accessToken);
			return new ResponseEntity<Account>(HttpStatus.OK);
		} catch (NoSuchTupleException | PlaidException | NoExistingAccountsException e) {
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Retrieves an account with an account Id that is associated with the user.
	 * @param accountId FIXME we may need to specify between accountId and userId
	 * @param userId FIXME defer to the above.
	 * @param accessToken FIXME we may or may not need the access token here and instead get it later in the service calls
	 * @return A string representing a JSON object of the specified account.
	 */
	@GetMapping(value="/getAccount")
	public ResponseEntity<String> getAccount(){
		String accessToken = "access-sandbox-bd815122-d735-41bf-8119-08cdab46099d";
		int id = 1;
		
		
			try {
				String foundAccount = userService.getAccount(id);
				System.out.print(foundAccount);
				return ResponseEntity.ok(foundAccount);
			} catch (NoSuchTupleException e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			} catch (PlaidException e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		}
	
	/**
	 * Retrieves all accounts that a user owns.
	 * @param accessToken FIXME again this might be something that is provided by a userId
	 * @return
git a	 */
	@GetMapping(value="/getAccounts")
	public ResponseEntity<List<String>> getAccounts(){
		String accessToken = "access-sandbox-bd815122-d735-41bf-8119-08cdab46099d";
		
		try {
			List<String> foundAccounts = userService.getAllAccounts(1);
			return ResponseEntity.ok(foundAccounts);
		} catch (NoSuchTupleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (PlaidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Retrieves the transactions from a provided account.
	 * @param accountId The account id to retrieve the account ids from. FIXME it may need to change from string to int.
	 * @return A string representing the transactions for an account.
	 */
	@GetMapping(value="/getTransactions")
	public ResponseEntity<List<String>> getTransactions(@RequestBody String accountId){
		
		try {
			List<String> transactions = userService.getTransactionsForAccount(Integer.valueOf(accountId));
			return ResponseEntity.ok(transactions);
		} catch (NoSuchTupleException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (PlaidException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
