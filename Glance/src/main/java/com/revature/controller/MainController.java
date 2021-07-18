package com.revature.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//Spring Web Includes
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.revature.entities.Account;
//Glance Project Includes
import com.revature.entities.User;
import com.revature.exceptions.ExistingAccountException;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.NoExistingAccountsException;
import com.revature.exceptions.NoSuchTupleException;
import com.revature.exceptions.PlaidException;
import com.revature.service.UserService;

/**
 * A rest controller that provides application endpoints to the front end for user control and
 * accessing information that is tied to specific users.
 * @author Primary Author: Kyle Castillo, Secondary Authors: James Butler, Jack Welsh, Nse Obot
 *
 */
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
	 * Accepts user credentials and tests them against registered users. A match logs the user in and redirects them to their accounts overview.
	 * @param user: A user object with only the email address (used as the users ID for logging in) and their password.
	 * @return a ModelAndView either returning a redirect to accounts overview and a session cookie containing the user ID and a login flag or a redirect to the landing page if the user is not found or the password is incorrect.
	 */
	@PostMapping(value="/login")
	public ModelAndView loginUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute User user, Model model){
		
		
		try {
			User u = userService.login(user.getEmail(), user.getPassword());
			model.addAttribute("user",u);
			userCookieManager(request, response, u);
			return  new ModelAndView("redirect:/overview.html");
		} catch (InvalidPasswordException | NoSuchTupleException e) {
			return new ModelAndView("redirect:/index.html");
		}
	}
	
	

	
	
	/**
	 * Accepts a user generated from the thymeleaf annotated sign-up form and attempts to insert that user into the databse. A successful insert with redirect the new user to add their accounts. An unsuccessful attempt will redirect the user to either try again or to login if the user is already registered.
	 * @param request : The http request sent. This is auto-generated and is required for cookie creation management.
	 * @param response : The http response for the request. Required for cookie creation management.
	 * @param user : A user-object constructed from the form data of the thymeleaf annotated form on the front end. 
	 * @returna Redirect view that redirects the user based on the result of their registration. A successful registration redirects to account linking. If a user already exists with the provided uesername, the user is directed to log in. All other errors redirect to the signup form to try again.
	 */
	@PostMapping(value="/register")
	public RedirectView registerUser (HttpServletRequest request, HttpServletResponse response, User user) {
		
		
		try {
			User createdUser = userService.createNewUser(user);
			userCookieManager(request, response, createdUser);
			return new RedirectView("/link.html");
			//return ResponseEntity.ok(createdUser);
		} catch (ExistingAccountException e) {
			e.printStackTrace();
			return new RedirectView("/signup.html");
			//return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			return new RedirectView("/signup.html");
			//return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	

	/**
	 * Consumes an access token to obtain the all accounts a specified user has at an institution and persist the necessary information to retrieve them for further use.
	 * @param userId : the internal auto-generated user ID from the userCookie. 
	 * @param plaidAccessToken : the access token generated by the token exchange, provided from 
	 * @return A response entity denoting either success or failure of the operation.
	 */
	@GetMapping(value="/addAccount")
	public @ResponseBody ResponseEntity<Account> addAccount(@RequestParam int userId, @RequestParam String plaidAccessToken) {
		
		System.out.println("User ID: " + " " + userId + "AccessToken: " + plaidAccessToken);
		try {
			userService.addAccounts(userId, plaidAccessToken);
			return new ResponseEntity<Account>(HttpStatus.OK);
		} catch (NoSuchTupleException | PlaidException | NoExistingAccountsException e) {
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	/**
	 * Retrieves the information from a singular account for a given user and returns it as a string for processing in the front end for displaying singular account details.
	 * This is a future functionality method that should not be used in the current release. 
	 * @param actId : The id number of the account to be retrieved.
	 * @return A string that contains the account information which will be consumed and parsed within the front end to display the account's information to the user.
	 */
	@GetMapping(value="/getAccount")
	public ResponseEntity<String> getAccount(HttpServletResponse response, @CookieValue(activeAccountCOOKIE) Cookie activeAcc){
		
		
			try {
				String foundAccount = userService.getAccount(activeAcc.getValue());
				return ResponseEntity.ok(foundAccount);
			} catch (NoSuchTupleException e) {
				//e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			} catch (PlaidException e) {
				//e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		}
	
	
	/**
	 * Returns all accounts associated with a specified user for processing and display on the accounts overview page.
	 * @param userId : the internal userId. Provided from the session cookies.
	 * @return : a list of strings of the accounts associated with a user.
	 */
	@GetMapping(value="/getAccounts")
	public ResponseEntity<List<String>> getAccounts(HttpServletResponse response, @CookieValue(accountsCOOKIE) Cookie accountsCookie, @RequestParam int userId){
		try {
			List<String> foundAccounts = userService.getAllAccounts(userId);
			//System.out.println("\n USER_ID FROM PARAM: " + userId);
			//System.out.println("\n foundAccounts: " + foundAccounts);
			
			addAccountCookies(accountsCookie, pruneAccountsForID(foundAccounts));
			response.addCookie(accountsCookie);
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
	 * @param accs - full accounts that will be parsed for their ids 
	 * @return List<String> - list of pure account ids 
	 */
	private List<String> pruneAccountsForID(List<String> accs) {
		List<String> accIDs = new ArrayList<>();
		final String term = "accountId: ";
		final String delim = "\n";
		
		//isolates id from grand string by splitting it
		accs.forEach(s -> accIDs.add(s.split(term)[1].split(delim)[0]));	
		
		System.out.println("Just the id: " + accIDs);
		System.out.println("\n\n\n");
		return accIDs;
	}

	
	/**
	 * Returns the list of transactions associated with a singular account for a user as a list of strings to be parsed and displayed within the front end. 
	 * This is a future functionality method that should not be used in the current release.
	 * @param accountId
	 * @return
	 */
	@GetMapping(value="/getTransactions")
	public ResponseEntity<List<String>> getTransactions(@CookieValue(activeAccountCOOKIE) Cookie activeAcc){
		
		try {
			List<String> transactions = userService.getTransactionsForAccount(activeAcc.getValue());
			return ResponseEntity.ok(transactions);
		} catch (NoSuchTupleException e) {
			//e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (PlaidException e) {
			//e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Logs out the current user but removing the set cookies that provide session control and then redirecting the user to the landing page.
	 * @param request : The Http request that is generated when logout is selected by the user. Used for cookie management.
	 * @param response : the generated response. Used for cookie management.
	 * @return : RedirectView that send the now logged-out user to the landing page.
	 */
	@GetMapping(value="/logout")
	public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
		//Clearing the cookies
		clearCookies(request, response);
		
		//Returning to index.html
		return new RedirectView("/index.html");
	}
	
	
	/**
	 * A utility method that sets the user session cookie on successful login or registration. This method should only be called from within functions providing login or registration functionality.
	 * @param request : The Http request
	 * @param response : The Http response
	 * @param u : The user that was either registered or logged in.
	 */
	
	//Cookie ID's should be accessible everywhere so they can be accessed and eddited if necessary
	final String userIdNameCOOKIE = "userId";
	final String userIsLoggedInCOOKIE = "userIsLoggedIn";
	final String accountsCOOKIE = "accounts";
	final String activeAccountCOOKIE = "activeAccount";
	private void userCookieManager(HttpServletRequest request, HttpServletResponse response, User u) 
	{
		//User Cookie
		final String userIdCookieValue = String.valueOf(u.getId());
		
		//logged In Flag
		final String userIsLoggedInCookieValue = "True";
		
		//Acccount IDs
		
		final boolean useSecureCookie = false;
		final int expiryTime = 60* 60 * 24;
		final String cookiePath = "/";
		
		
		Cookie userIsLoggedInCookie = new Cookie(userIsLoggedInCOOKIE, userIsLoggedInCookieValue);
		Cookie userIdCookie = new Cookie(userIdNameCOOKIE, userIdCookieValue);
		Cookie accounts = new Cookie(accountsCOOKIE, "");
		Cookie activeAccount = new Cookie(activeAccountCOOKIE, "qBxX33VrbMHknnnKdkVEckMWP6BGoGUJpyy5m");
		
		Cookie[] arr = new Cookie[] {userIdCookie, userIsLoggedInCookie, accounts, activeAccount};
		
		for (Cookie c : arr) {
			c.setSecure(useSecureCookie);
			c.setMaxAge(expiryTime);
			c.setPath(cookiePath);
			
			response.addCookie(c);
		}
		
	}
	//END COOKIE MANAGER METHOD
	
	
	
	
	/**
	 * 	We add a cookie for account ID's so we can access them from the front end
	 *  We send the PLAID account ID's to the front end
	 * 
	 * @param http request from front end, holds our cookies
	 * @param response, we will add editted cookie value back here, may not need to
	 * @param foundAccounts, list of our accounts recovered from user at this id
	 */
	private void addAccountCookies(Cookie accountsCookie,
			List<String> accIDs) {
		
		//add all account id's from found accounts
		StringBuilder temp = new StringBuilder("");
		accIDs.forEach(id -> temp.append(id + "---"));
		String value = accIDs.size() > 1 ? temp.substring(0, temp.length()-3) : "";
		accountsCookie.setValue(value);
		
		/*
		 * System.out.println("\n------------------------");
		 * System.out.println("ACCOUNT COOKIES: " + accountsCookie.getValue());
		 * System.out.println("\n------------------------");
		 */
	}
	//END ADD_ACCOUNT_COOKIES
	
	
	/**
	 * A utility method that clears all session cookies. This method should only be called from within other methods that provide logout functionality.
	 * @param request The Http request
	 * @param response The Http response
	 */
	private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			cookie.setPath("");
			cookie.setValue(null);
			response.addCookie(cookie);
		}
	}
}
//END CLASS Main Controller
