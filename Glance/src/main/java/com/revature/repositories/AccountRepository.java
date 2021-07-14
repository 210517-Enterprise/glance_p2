package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	/**
	 * Gets a list of <code>Account</code> entities based on a provided user id.
	 * @param userId the foreign key id which references a <code>User</code> entity.
	 * @return a list of <code>Account</code> entities or <code>null</code> if nothing was found.
	 */
	List<Account> findAccountByUserId(int userId);
	
	@Query(value="select a.plaidItem from Account a where a.id=:id")
	String findPlaidItemById(int id);
	
	@Query(value="select a.plaidKey from Account a where a.id=:id")
	String findPlaidKeyById(int id);
}
