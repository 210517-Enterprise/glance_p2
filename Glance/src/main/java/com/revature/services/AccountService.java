package com.revature.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.entities.Account;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.AccountRepository;

/**
 * The account service for the glance application.
 * @author Kyle Castillo
 *
 */
@Service
public class AccountService {

	
	private AccountRepository accountRepo;
	
	@Autowired
	AccountService(AccountRepository accountRepo){
		this.accountRepo = accountRepo;
	}
	
	/**
	 * Retrieves all accounts that belong to a user id.
	 * @param userId The user id which references a user that owns the account
	 * @return a list of accounts that belong to the user with the userId.
	 */
	@Transactional(readOnly = true)
	public List<Account> getAllAccountsByUserId(int userId){
		List<Account> accounts = accountRepo.findAccountsByUserId(userId);
		
		if(accounts.isEmpty() || accounts.equals(null))
			throw new ResourceNotFoundException();
		return accounts;
	}
	
	/**
	 * Retrieves a singular account from the account id.
	 * @param accountId the account id to query by.
	 * @return A single Account found by the id provided.
	 */
	@Transactional(readOnly = true)
	public Account getAccountById(int accountId) {
		Account account = accountRepo.findById(accountId).get();
		
		if(account.equals(null))
			throw new ResourceNotFoundException();
		return account;
	}
	
	@Transactional(readOnly = true)
	public String getPlaidItem(int accountId) {
		String plaidItem = accountRepo.findPlaidItemByAccountId(accountId);
		
		if(plaidItem.equals(null))
			throw new ResourceNotFoundException();
		return plaidItem;
	}
	
	@Transactional(readOnly = true)
	public String getPlaidKey(int accountId) {
		String plaidKey = accountRepo.findPlaidKeyByAccountId(accountId);
		
		if(plaidKey.equals(null))
			throw new ResourceNotFoundException();
		return plaidKey;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveNewAccount(@Valid Account newAccount) {
		accountRepo.save(newAccount);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteAccountById(int accountId) {
		accountRepo.delete(getAccountById(accountId));
	}
}
