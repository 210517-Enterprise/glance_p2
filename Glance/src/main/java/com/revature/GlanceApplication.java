package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Glance is a personal finance application that allows users to collect information about all of their accounts across
 * multiple institutions in a single app and view that information. The application makes use of the Plaid API and users can
 * link to accounts at any institution participating in the API. Users may currently register, login, and view an overview of their accounts.
 * 
 * Future Functionality: View details of individual accounts. Set personal spending and savings goals. View progress towards meeting set goals.
 * @author James Butler, Kyle Castillo, Jack Walsh, and Nse Obot
 *
 */
@SpringBootApplication
public class GlanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlanceApplication.class, args);
	

	}

}
