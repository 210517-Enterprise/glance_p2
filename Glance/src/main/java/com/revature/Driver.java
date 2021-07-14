package com.revature;

import java.io.IOException;

import com.revature.controller.PlaidController;
import com.revature.controller.PlaidController.LinkToken;

public class Driver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PlaidController pc = new PlaidController();
		
		LinkToken token = pc.createToken();
		System.out.println(token.linkToken);
		
		System.out.println(pc.getAccounts("access-sandbox-bbd17205-1734-41cb-9536-77146d0ffd77"));
		System.out.println(pc.getAccounts1("access-sandbox-bbd17205-1734-41cb-9536-77146d0ffd77"));
	}

}
