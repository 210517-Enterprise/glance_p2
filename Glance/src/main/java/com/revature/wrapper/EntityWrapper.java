package com.revature.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.Data;

/*
 *  Entity Wrapper is a transitory class that "wraps" the Entity data we want to send
 *  to the front end.
 *  	
 *  	Each EntityWrapper consists of:
 *  		- Error Code: 0 for no error, else positive for some corresponding internal error or -1 for critical error converting to JSON
 *  		- Error Message: Loaded from exception that throws the error, briefly describes the problem
 *  		- data: Contains the actual data we expect, contained within the class as a Java object, but 
 *  			sent to the front end
 *  
 *    
 *    	Example JSON Object to be sent to front end, for an account:
 *    
 *    		{
 *    			"errorCode" : "0",
 *    			"errorMsg" : "",
 *    			"data: : { 
 *    						"id" : "###",
 *    						"user_id" : "###",
 *    						"goal_id" : "###",
 *  						"plaid_item" : "###",
 *    						"palid_key" : "###",
 *    						"creationDate" : "###",
 *    						...  
 *    						}
 *    		}
 */

@Data
public class EntityWrapper {

	//Variables
	int errorCode;
	String errorMsg;
	Object data;
	
	
	//Public Constructor
	public EntityWrapper(int errorCode, String errorMsg, Object data) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.data = data;
	}
	
	
	
	/** Takes no parameters
	 * @return ITSELF, all data contained in this entity as JSON
	 */
	public String asJSON() {
		
		ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT );
	    String json = "{\"errorCode\" : \"-1\",\r\n"
	    		+ "\"errorMsg\" : \"Critical Erorr, conversion to JSON failed\",}" +
	    		"data: : {} }";
		try {
			json = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    System.out.println("Convert List to JSON :");
	    System.out.println(json);
	    
		return json;
	}
	
}
//END CLASS ENTITY WRAPPER