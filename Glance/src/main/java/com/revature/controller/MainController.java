package com.revature.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping(value="/login")
	public ResponseEntity<User> loginUser(@ModelAttribute User user, Model model){
		model.addAttribute("user",user);
		System.out.println(user);
		try {
			User foundUser = userService.login(user.getEmail(), user.getPassword());
			return ResponseEntity.ok(foundUser);
		} catch (InvalidPasswordException | NoSuchTupleException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PostMapping(value="/register")
	public ResponseEntity<User> registerUser (@ModelAttribute User user, Model model) {
		
		model.addAttribute("user", user);
		
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
	
	//FIXME change the return type to not be String
	@GetMapping(value="/addAccount")
	public @ResponseBody ResponseEntity<Account> addAccount() {
		String accessToken = "access-sandbox-bd815122-d735-41bf-8119-08cdab46099d";
		int id = 1;
		System.out.println(accessToken + " " + id);
		
		try {
			List<Account> newAccounts = userService.addAccounts(id, accessToken);
			return new ResponseEntity<Account>(HttpStatus.OK);
		} catch (NoSuchTupleException | PlaidException | NoExistingAccountsException e) {
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		}
	}
	
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
}
