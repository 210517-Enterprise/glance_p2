package com.revature.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//Spring Web Includes
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.User;
import com.revature.exceptions.ExistingAccountException;
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
}
