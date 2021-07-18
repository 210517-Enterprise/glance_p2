package com.revature.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.plaid.client.ApiClient;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import com.plaid.client.model.CountryCode;
import com.plaid.client.model.Error;
import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.model.LinkTokenCreateRequest;
import com.plaid.client.model.LinkTokenCreateRequestUser;
import com.plaid.client.model.LinkTokenCreateResponse;
import com.plaid.client.model.Products;
import com.plaid.client.model.TransactionsGetRequest;
import com.plaid.client.model.TransactionsGetRequestOptions;
import com.plaid.client.model.TransactionsGetResponse;
import com.plaid.client.request.PlaidApi;

import retrofit2.Response;


/**
 * Controller that provides an interface with the Plaid API and manages all API calls.
 * @author James Butler and Nse Obot.
 *
 */
@RestController
@RequestMapping("/api")
public class PlaidController {

	  private final PlaidApi plaidClient;
	  private final ApiClient apiClient;
	  HashMap<String, String> apiKeys = new HashMap<String, String>();
	
	  /**
	   * Constructor for the plaid controller which also sets its base configuration.
	   */
	public PlaidController () {
		apiKeys.put("clientId", "60e742aabd21f9000f425a75");
		apiKeys.put("secret", "3bb6bef69833623e0e66d14abd6950");
		apiKeys.put("plaidVersion", "2020-09-14");
		this.apiClient = new ApiClient(apiKeys);
		this.apiClient.setPlaidAdapter(ApiClient.Sandbox); // or equivalent, depending on which environment you're calling into
		this.plaidClient = apiClient.createService(PlaidApi.class);
	}
	
	/**
	 * Retrieves a new link token to begin the link process with the Plaid API. LINK is a black box front-end component that consumes LinkToken.
	 * @return A LinkToken for consumption by the LINK component within the front end.
	 * @throws IOException Thrown if there is a problem with Plaid or, more likely, it was configured improperly above.
	 */
	@PostMapping(value="linktoken/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public LinkToken createToken () throws IOException {
		String clientUserId = Long.toString((new Date()).getTime());
	    LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
			.clientUserId(clientUserId);

			LinkTokenCreateRequest request = new LinkTokenCreateRequest()
				.user(user)
				.clientName("Quickstart Client")
				.products(Arrays.asList(Products.AUTH, Products.TRANSACTIONS))
				.countryCodes(Arrays.asList(CountryCode.US, CountryCode.CA))
				.language("en");

	    	Response<LinkTokenCreateResponse> response = plaidClient
				.linkTokenCreate(request)
				.execute();
	    return new LinkToken(response.body().getLinkToken());
	}
	

	/**
	 * Retrieves access token
	 * @param String publicToken
	 * @return String [] of accessToken and itemId
	 */
	/**
	 * Takes in a public token provided by LINK and exchanges it for an access token that is tied to a specific set of accounts in Plaid and a specific user in Glance.
	 * @param rep The HTTP Response which is used for cookie management
	 * @param req The HTTP request which is used for cookie management
	 * @param publicToken An intermediary token (valid for 30 minutes) that was provided by the LINK component and will be exchanged.
	 * @return accessToken The access token needed for future interaction with the Plaid API. It is used in calls to the API to return information bound for a specific person and from a specific institution. A User must have a token for EACH institution they wish to view information form.
	 * @throws IOException Thrown for any errors encountered, primarily from failure of LINK or bad configuration of the Plaid API client.
	 */
	@PostMapping(value="linktoken/exchange")
	public String[] exchangeToken(HttpServletResponse rep, HttpServletRequest req, @RequestBody String publicToken) throws IOException {
		//System.out.println(publicToken);
		String public_token = publicToken.substring(0, (publicToken.length() - 1));
		System.out.println(public_token);
		String[] access_tokens = new String[2];
		ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
			      .publicToken(public_token);
		Response<ItemPublicTokenExchangeResponse> response = plaidClient
			      .itemPublicTokenExchange(request)
			      .execute();
		
		access_tokens[0] = response.body().getAccessToken();
		access_tokens[1] = response.body().getItemId();
		
		//write more code here to persist these to the DB where they belong
		tokenManager(rep,req,access_tokens[0]);
		return access_tokens;
	}
	
	/**
	 * Token manager to save the plaidAccessToken as a cookie to the current session.
	 * @param response The Http response
	 * @param request The Http request
	 * @param plaidAccessToken the access token that was created by plaid.
	 */
	private void tokenManager(HttpServletResponse response, HttpServletRequest request, String plaidAccessToken) {
		final String cookieName = "plaidAccessToken";
		final String cookieValue = plaidAccessToken;
		final boolean useSecureCookie = false;
		final int expiryTime = 60* 60 * 24;
		final String cookiePath = "/";
		
		Cookie cookie = new Cookie(cookieName, cookieValue);
		
		cookie.setSecure(useSecureCookie);
		cookie.setMaxAge(expiryTime);
		cookie.setPath(cookiePath);
		
		response.addCookie(cookie);
	}
	
	
	/**
	 * Retrieves account "balances." Despite the name, the "balances" contain all relevant account data, such as IDs, institutions, and account types in addition to the actual balances. The name is chosen by the Plaid API and reflects that Balances always returns the most current information instead of information from Plaid's cache.
	 * @param accessToken The Access Token from LINK. 
	 * @return A JSON object containing the information for every account associated with an access token. Further processed elsewhere.
	 * @throws IOException Thrown for any error in the Plaid API or invalid input.
	 */
	@GetMapping(value="balances/get")
	public AccountsGetResponse getAccounts(@RequestBody String accessToken) throws IOException {
	    AccountsBalanceGetRequest request = new AccountsBalanceGetRequest()
	      .accessToken(accessToken);

	    //System.out.println(accessToken);
	    //System.out.println(request);
	    
	    Response<AccountsGetResponse> response = plaidClient
	      .accountsBalanceGet(request)
	      .execute();
	    
	    //System.out.println(response);
	    return response.body();
	  }

	/**
	 * Utility class to hold the LinkToken, which is produced and consumed entirely within the Plaid Controller and is not needed anywhere else.
	 *
	 */
	public static class LinkToken {
	    @JsonProperty
	    public String linkToken;


	    public LinkToken(String linkToken) {
	      this.linkToken = linkToken;
	    }
	  }
	
	/**
	 * Retrieves account transactions
	 * @param accessToken The Access Token from LINK.
	 * @return account transactions as json String
	 */
	@PostMapping(value="transactions/get")
	public TransactionsGetResponse getTransactions(String accessToken) throws IOException {
	    LocalDate startDate = LocalDate.now().minusDays(30);
	    LocalDate endDate = LocalDate.now();

	    TransactionsGetRequestOptions options = new TransactionsGetRequestOptions()
	    .count(100);

	    TransactionsGetRequest request = new TransactionsGetRequest()
	      .accessToken(accessToken)
	      .startDate(startDate)
	      .endDate(endDate)
	      .options(options);

	    Response<TransactionsGetResponse> response = null;
	    for (int i = 0; i < 5; i++){
	      response = plaidClient.transactionsGet(request).execute();
	      if (response.isSuccessful()){
	        break;
	      } else {
	        try {
	          Gson gson = new Gson();
	          Error error = gson.fromJson(response.errorBody().string(), Error.class);
	          error.getErrorType().equals(Error.ErrorTypeEnum.ITEM_ERROR);
	          error.getErrorCode().equals("PRODUCT_NOT_READY");
	          Thread.sleep(3000);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    return response.body();
	  }
	
}
