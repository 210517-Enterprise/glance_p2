package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	List<Account> findAccountsByUserId(int userId);
	
	@Query(value="select plaid_item from accounts where id=:id")
	String findPlaidItemByAccountId(int id);
	
	@Query(value="select plaid_key from accounts where id=:id")
	String findPlaidKeyByAccountId(int id);
}
