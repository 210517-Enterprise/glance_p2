package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.User;

/**
 * User Repository for glance users.
 * @author Kyle Castillo
 * @version 0.0.1
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	/**
	 * Finds a user by their id.
	 * @param id the id to query by.
	 * @return A <code>User</code> object or <code>null</code> if no user was found.
	 */
	User findUserById(int id);
	
	/**
	 * Finds a user by their email.
	 * @param email the email to query by.
	 * @return A <code>User</code> object or <code>null</code> if no user was found.
	 */
	User findUserByEmail(String email);
	
	/**
	 * Finds the users by their main address.
	 * @param mainAddress the main address to query by.
	 * @return A list of <code>User</code> objects or <code>null</code> if no users were found.
	 */
	List<User> findUserByMainAddress(String mainAddress);
	
	/**
	 * Finds the users by their alternate address.
	 * @param altAddress the alternate address to query by.
	 * @return A list of <code>User</code> objects or <code>null</code> if no users were found.
	 */
	List<User> findUserByAltAddress(String altAddress);
	
	/**
	 * Finds the password of a user based on their id.
	 * @param id the id of the user.
	 * @return a string of the hashed password or <code>null</code> if no password was found.
	 */
	@Query(value = "select u.password from User u where u.id=:id")
	String findPasswordById(int id);
	
	/**
	 * Finds the email of a user based on their id.
	 * @param id the id of the user.
	 * @return a string of the email or <code>null</code> if no email was found.
	 */
	@Query(value = "select u.email from User u where u.id=:id")
	String findEmailById(int id);
	
	/**
	 * Updates the password of a user that matches the id.
	 * @apiNote Its recommended to hash the <code>password</code> prior to updating.
	 * @param id the id of the user.
	 * @param password the new <code>password</code> to replace the hold password.
	 */
	@Query(value = "update User u set u.password=:password where u.id=:id")
	void updateUserPassword(int id, String password);
	
	@Query(value = "update User u set u.password=:password where u.email=:email")
	void updateUserPassword(String email, String password);
	
	@Query(value = "update User u set u.mainAddress=:mainAddress where u.id=:id")
	void updateUserMaindAddress(int id, String mainAddress);
	
	@Query(value = "update User u set mainAddress=:mainAddress where u.email=:email")
	void updateUserMainAddress(String email, String mainAddress);
	
	@Query(value="update User u set u.altAddress=:altAddress where u.id=:id")
	void updateUserAltAddress(int id, String altAddress);
	
	@Query(value="update User u set u.altAddress=:altAddress where u.email=:email")
	void updateUserAltAddress(String email, String altAddress);
	
	@Query(value ="update User u set u.cellphone=:cellphone where u.id=:id")
	void updateUserCellphone(int id, String cellphone);
	
	@Query(value ="update User u set u.cellphone=:cellphone where u.email=:email")
	void updateUserCellphone(String email, String cellphone);
}
