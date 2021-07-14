package com.revature.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//Spring Web Includes
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.User;

//Glance Project Includes


@RestController
public class MainController {

	
	/*
	 * Implemented by Kyle
	 * 
	 * Will direct resources to front-end for users
	 */
	
	@PostMapping(value="/register")
	public String registerUser (@ModelAttribute User user, Model model) {
		model.addAttribute("user", user);
		System.out.println(model.getAttribute("user"));
		return "result";
	}
}
