package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.User;

/**
 * User Repository for glance users. Currently the following queries are implemented in addition
 * to the JpaRepository queries:
 * <ul>
 * <li> Finding a user by their id </li>
 * <li> Finding a user by their email </li>
 * <li> Finding users by their main address </li>
 * <li> Finding users by their alternate address </li>
 * <li> Updating a user's password using their id or email </li>
 * <li> Updating a user's main or alternate address by using their id or email </li>
 * <li> Updating a user's cell phone by using their id or email </li>
 * <br>
 * @author Kyle Castillo
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findUserById(int userID);
	User findUserByEmail(String email);
	List<User> findUsersByMainAddress(String address);
	List<User> findUsersByAltAddress(String address);
	
	@Query(value = "update User set password=:password where id=:id")
	void updateUserPassword(int id, String password);
	
	@Query(value = "update User set password=:password where email=:email")
	void updateUserPassword(String email, String password);
	
	@Query(value = "update User set main_address = :address where id = :id")
	void updateUserMaindAddress(int id, String address);
	
	@Query(value = "update User set main_address = :address where email=:email")
	void updateUserMainAddress(String email, String address);
	
	@Query(value="update User set alt_address =:address where id=:id")
	void updateUserAltAddress(int id, String address);
	
	@Query(value="update User set alt_address=:address where email=:email")
	void updateUserAltAddress(String email, String address);
	
	@Query(value ="update User set cellphone = :phoneNumber where id = :id")
	void updateUserCellphone(int id, String phoneNumber);
	
	@Query(value ="update User set cellphone = :phoneNumber where email = :email")
	void updateUserCellphone(String email, String phoneNumber);
}
