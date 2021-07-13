package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.entities.User;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepo;
	
	@Autowired
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Transactional(readOnly = true)
	public User getUser(int id) {
		User user = userRepo.findUserById(id);
		
		if(user.equals(null))
			throw new ResourceNotFoundException();
		
		return user;
	}
}
