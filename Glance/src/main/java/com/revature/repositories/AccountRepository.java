package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Account;

/**
 * The accounts repository provides necessary CRUD functionality dealing with account objects within the database.
 * @author Kyle Castillo
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	/**
	 * Gets a list of <code>Account</code> entities based on a provided user id.
	 * @param userId the foreign key id which references a <code>User</code> entity.
	 * @return a list of <code>Account</code> entities or <code>null</code> if nothing was found.
	 */
	List<Account> findAccountByUserId(int userId);
	
	/**
	 * Gets a list of <code>Accounts</code> entities based on (and associated with) the provided plaid Item.
	 * @param plaidItem plaidItem is the unique Plaid API item that is associated with accounts.
	 * @return List of all accounts associated with that Plaid ID
	 */
	List<Account> findAccountByPlaidItem(String plaidItem);
	
	/**
	 * Returns a specific plaidItem for an <code>Account</code> which can be used to to query the Plaid API for additional accounts that are within the same group within the Plaid API.
	 * @param id The internal account id for the account.
	 * @return A string of the plaidItem
	 */
	@Query(value="select a.plaidItem from Account a where a.id=:id")
	String findPlaidItemById(int id);
	
	/**
	 * Returns a specific plaidKey for an <code>Account</code> which can be used to to query the Plaid API for detailed information about the account.
	 * @param id The internal account id for the account.
	 * @return A string of the plaidKey.
	 */
	@Query(value="select a.plaidKey from Account a where a.id=:id")
	String findPlaidKeyById(int id);
	
	/**
	 * Updates the plaidKey for an account if it has changed.
	 * @param id The internal account id for the account.
	 * @param plaidKey The new plaidKey for the account.
	 * @return a String containing the results of the update call.
	 */
	@Query(value="update Account a set a.plaidKey=:plaidKey where a.id=:id")
	String updatePlaidKeyById(int id, String plaidKey);
}
