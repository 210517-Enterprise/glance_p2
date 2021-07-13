package com.revature.repositories;

import com.revature.entities.User;

public class UserDAOImpl implements UserDAOInterface {

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Implementation of user dao interface that makes calles
	 * to our PostgresSQL DB via Spring data dependencies (JPA and hibernate) 
	 * 
	 * We will take advanatage of the the fact that our classes our annoated
	 * spring bean, spring data objects and the spring data JPA
	 */
	
	
	
}
