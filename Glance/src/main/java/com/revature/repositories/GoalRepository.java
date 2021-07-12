package com.revature.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Goal;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Integer>{
	
	
	Goal findGoalById(int id);
	List<Goal> findGoalsByName(String name);
	List<Goal> findGoalsByUserId(int id);
	List<Goal> findGoalsByAccountId(int id);
	//TODO Implementing a query to find users before a given date.
	
	@Query("update Goal goal_amount=:amount where id=:id")
	void updateGoalAmount(int id, int amount);
	
	@Query("update Goal goal_amount=:amount where goal_name =:name AND user_id =:userID")
	void updateGoalAmount(String name, int userID, int amount);
	
	//TODO implementing setting the finish date for a goal.
}
