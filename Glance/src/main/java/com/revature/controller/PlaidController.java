package com.revature.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plaid.client.ApiClient;
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
			
			System.out.println(request);

	    	Response<LinkTokenCreateResponse> response = plaidClient
				.linkTokenCreate(request)
				.execute();
	    return new LinkToken(response.body().getLinkToken());
	}
	
	@PostMapping(value="linktoken/exchange", produces = MediaType.APPLICATION_JSON_VALUE)
	public String exchangeToken(@RequestBody String publicToken) throws IOException {
		System.out.println(publicToken);
		
		
		
		ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
				  .publicToken(publicToken)
				  .clientId(apiKeys.get("clientId"))
				  .secret(apiKeys.get("secret"));
		
		System.out.println(request);
				Response<ItemPublicTokenExchangeResponse> response = plaidClient
				  .itemPublicTokenExchange(request)
				  .execute();
				String accessToken = response.body().getAccessToken();
				return accessToken;
	}

	public static class LinkToken {
	    @JsonProperty
	    private String linkToken;


	    public LinkToken(String linkToken) {
	      this.linkToken = linkToken;
	    }
	  }
	
}