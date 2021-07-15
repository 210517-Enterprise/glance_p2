package com.revature;

import java.io.IOException;

import com.revature.controller.PlaidController;
import com.revature.controller.PlaidController.LinkToken;
import com.revature.entities.User;
import com.revature.exceptions.InvalidPasswordException;
import com.revature.exceptions.NoSuchTupleException;
import com.revature.service.UserService;
import com.revature.*;

public class Driver {

	public static void main(String[] args) throws IOException {
		PlaidController pc = new PlaidController();
		
		LinkToken token = pc.createToken();
		System.out.println(token.linkToken);
		
		System.out.println(pc.getAccounts("access-sandbox-bd815122-d735-41bf-8119-08cdab46099d"));

		User u = new User("sampleEmail", "samplePass", null, null, null, null);
		UserService.createNewUser(u);
		User v = null;
		try {
			v = UserService.login(u.getEmail(), u.getPassword());
		} catch (InvalidPasswordException | NoSuchTupleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("the user is: " + v);

		System.out.println(pc.getTransactions("access-sandbox-bd815122-d735-41bf-8119-08cdab46099d"));
	}

}
