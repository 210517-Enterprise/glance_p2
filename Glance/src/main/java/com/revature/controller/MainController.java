package com.revature.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//Spring Web Includes
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.User;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.NoSuchTupleException;
import com.revature.service.UserService;

//Glance Project Includes


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
		
		/*
		 * Created User will never be null, we throw either an
		 * 	- InvalidPassWordException or
		 *  - AccountAlreadyExists Exception in case of error
		 *  
		 *  Additionally, the createNewUser and login methods are intended to be static
		 *  as they are not associated with an internal user account - an INSTANCE of 
		 *  userService should *always* we associated with a logged in user, else
		 *  we would have no reference of who's data to draw
		 *  
		 *  Whatever value (exception or otherwise) emerges from User service
		 *  should be wrapped in EntityWrapper with an error code and message
		 *  erro code 0 for no error, and a null message and then the data
		 *  saved under the data field
		 *  
		 *   This entity wrapper will convert itself to JSON and be sent to the front
		 *   end in a standardized form
		 *   
		 *   See EntityWrapper Code.
		 *   
		 *   I would envision this method looking something like
		 *   
		 *   String errorCode = 1;
		 *   String errorMsg = "";
		 *   User createdUser = null; 
			try {
				createdUser = userService.createNewUser(user);
			} catch(InvalidPassWordException e) {
				errorCode = 1;
				errorMsg = e.getMessage()
			} catch(AccountAlreadyExistsException e) {
				errorCode = 2;
				errorMsg = e.getMessage();
			}
			
			return ResponseEntity.ok(new EntityWrapper(errorCode, errorMsg, createdUser));
		
		 * 
		 */
		
		model.addAttribute("user", user);
		
		User createdUser = UserService.createNewUser(user);
		
		if(createdUser == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(createdUser);
		}
	}
}
