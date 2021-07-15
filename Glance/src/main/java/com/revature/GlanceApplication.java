package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.revature.entities.User;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.NoSuchTupleException;
import com.revature.service.UserService;

@SpringBootApplication
public class GlanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlanceApplication.class, args);
	

	}

}
