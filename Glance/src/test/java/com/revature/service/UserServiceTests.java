package com.revature.service;

import org.junit.*;

//Project imports
import com.revature.entities.*;
import com.revature.repositories.*;
import com.revature.exceptions.*;


//Mockito, JUNIT Imports
import static org.mockito.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class UserServiceTests {

	
	@Before
	public void setUp() throws NoSuchTupleException 
	{
		//Setup our variables 
	}
	
	@After
	public void teardown() {
		//Teardown our variables 
	}
	
	
	/*
	 * Test Dependency Injection on UserDAO
	 */
	
	
	
	
	/*
	 * Test Dependency Injection on AccountsDAO
	 */
	
	
	
	
	//############################################################################
								//LOGIN METHOD TESTS
	//############################################################################
	
	
	/*
	 * Test if a user with an actual user and password
	 * is returned from the method
	 */
	@Test
	public void testRealEmailPassReturnsUser() {
		assertTrue(true);
	}
	
	/*
	 * Test if a nonexistent email throws 
	 * an invalid argument exception 
	 */
	@Test
	public void testBadEmailThrowsNoSuchTupleException() {
		assertTrue(true);
	}
	
	
	/*
	 * Test if a nonexistent email throws 
	 * an invalid argument exception 
	 */
	@Test
	public void testBadPasswordThrowsInvalidPasswordException() {
		assertTrue(true);
	}
	//END LOGIN METHOD TESTS
	
	
	//############################################################################
							//CREATE NEW USER TESTS
	//############################################################################
	
	/* #### - Test logic on create new user  */
	
	//############################################################################
							//ADD ACCOUNT TESTS
	//############################################################################
	
	

	/*- Test logic on AddAccount(itemID accesstoken)
		- throws error if access token is invalid
		- successfully adds legitimate account
	*/
	
	//############################################################################
						//GET ALL ACCOUNTS TESTS
	//############################################################################

	/*- Test logic on getAllAccountsInfo()
		- throws error on no accounts
		- correctly returns single string of JSON
	*/
		
	//############################################################################
						//GET ALL ACCOUNTS TESTS
	//############################################################################
		
		
	/*- Test logic on getAccountInfo(int accID)
		- throws error on no accounts
		- correctly returns single string of JSON

	 */
		

	//############################################################################
						//GET USER ID TESTS
	//############################################################################
	
	
	/*  - Test logic on getUserInfo()
		- correct JSON string is returned
	*/
	
}
//END UserServiceTests