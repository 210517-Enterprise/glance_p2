package com.revature.controller;

import java.io.IOException;
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
import com.plaid.client.ApiClient;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import com.plaid.client.model.CountryCode;
import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.model.LinkTokenCreateRequest;
import com.plaid.client.model.LinkTokenCreateRequestUser;
import com.plaid.client.model.LinkTokenCreateResponse;
import com.plaid.client.model.Products;
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
	
	@GetMapping(value="balances/get")
	public AccountsGetResponse getAccounts(@RequestBody String accessToken) throws IOException {
	    AccountsBalanceGetRequest request = new AccountsBalanceGetRequest()
	      .accessToken(accessToken);

	    Response<AccountsGetResponse> response = plaidClient
	      .accountsBalanceGet(request)
	      .execute();
	    return response.body();
	   
	  }
	
	@GetMapping(value="accounts/get")
	 public AccountsGetResponse getAccounts1(@RequestBody String accessToken) throws IOException {
	    AccountsGetRequest request = new AccountsGetRequest()
	    .accessToken(accessToken);
	    
	    System.out.println(request);

	    Response<AccountsGetResponse> response = plaidClient
	      .accountsGet(request)
	      .execute();
	    return response.body();
	  }

	public static class LinkToken {
	    @JsonProperty
	    public String linkToken;


	    public LinkToken(String linkToken) {
	      this.linkToken = linkToken;
	    }
	  }
	
}
