package com.revature.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

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



@RestController
@RequestMapping("/api")
public class PlaidController {

	  private final PlaidApi plaidClient;
	  private final ApiClient apiClient;
	  HashMap<String, String> apiKeys = new HashMap<String, String>();
	
	public PlaidController () {
		apiKeys.put("clientId", "60e742aabd21f9000f425a75");
		apiKeys.put("secret", "3bb6bef69833623e0e66d14abd6950");
		apiKeys.put("plaidVersion", "2020-09-14");
		this.apiClient = new ApiClient(apiKeys);
		this.apiClient.setPlaidAdapter(ApiClient.Sandbox); // or equivalent, depending on which environment you're calling into
		this.plaidClient = apiClient.createService(PlaidApi.class);
	}
	
	/**
	 * Retrieves link token
	 * @return LinkToken
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
	@PostMapping(value="linktoken/exchange")
	public String[] exchangeToken(@RequestBody String publicToken) throws IOException {
		System.out.println(publicToken);
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
		
		return access_tokens;
	}
	
	/**
	 * Retrieves account balances
	 * @param String accessToken
	 * @return account balance as json String
	 */
	@GetMapping(value="balances/get")
	public AccountsGetResponse getAccounts(@RequestBody String accessToken) throws IOException {
	    AccountsBalanceGetRequest request = new AccountsBalanceGetRequest()
	      .accessToken(accessToken);

	    System.out.println(accessToken);
	    System.out.println(request);
	    
	    Response<AccountsGetResponse> response = plaidClient
	      .accountsBalanceGet(request)
	      .execute();
	    
	    System.out.println(response);
	    return response.body();
	  }

	public static class LinkToken {
	    @JsonProperty
	    public String linkToken;


	    public LinkToken(String linkToken) {
	      this.linkToken = linkToken;
	    }
	  }
	
	/**
	 * Retrieves account transactions
	 * @param String accessToken
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
