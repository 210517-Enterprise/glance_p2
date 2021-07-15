package com.revature.controller;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import com.revature.controller.PlaidController.LinkToken;

@SpringBootTest
public class PlaidControllerTests {
	
	PlaidController pc = new PlaidController();
	
//	@Test
//	public void linkTokenTest() throws IOException {
//		LinkToken token = pc.createToken();
//		System.out.println(token.linkToken);
//	}
//	
//	@Test
//	public void getAccountsTest() throws IOException {
//		System.out.println(pc.getAccounts("access-sandbox-bd815122-d735-41bf-8119-08cdab46099d"));
//	}
//	
//	@Test
//	public void getTransactionsTest() throws IOException {
//		System.out.println(pc.getTransactions("access-sandbox-bd815122-d735-41bf-8119-08cdab46099d"));
//	}
}
