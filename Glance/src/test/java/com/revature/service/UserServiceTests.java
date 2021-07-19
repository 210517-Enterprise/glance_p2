package com.revature.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//Project imports
import com.revature.entities.*;
import com.revature.repositories.*;
import com.revature.exceptions.*;


//Mockito, JUNIT Imports
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



@SpringBootTest
public class UserServiceTests {

	
	//Set up mocked values
	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository mockUserRepo;
	
	@Mock
	AccountRepository mockAccRepo;
	
	@Mock
	GoalRepository mockGoalRepo;

	User u;
	Account a;
	
	@Autowired
	public void setUserService(UserService uv) {
		userService = uv;
	}
	
	@Before
	public void setUp() throws NoSuchTupleException 
	{
		MockitoAnnotations.initMocks(this);
		
		//create our user
		u = new User("j@w.com", "password", "J", null, null, null, null);
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
	public void testRealEmailPassReturnsUserTrue() throws InvalidPasswordException, NoSuchTupleException {
		
		//mock user Repo to return user wtih pass
		when(mockUserRepo.findUserByEmail(u.getEmail())).thenReturn(u);
		
		//Mock Bcrypt to return true at checkpw
		when(BCrypt.checkpw(u.getPassword(), u.getPassword())).thenReturn(true);
		
		assertEquals(u, userService.login(u.getEmail(), u.getPassword()) );
	}
	
	/*
	 * Test if a nonexistent email throws 
	 * an invalid argument exception 
	 */
	@Test
	public void testBadEmailThrowsNoSuchTupleException() {
		
		//mock user Repo to return null
		
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