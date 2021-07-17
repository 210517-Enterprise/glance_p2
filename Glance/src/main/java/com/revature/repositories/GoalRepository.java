package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Goal;

/**
 * The goal repository will provide CRUD operations for Goal objects necessary for functionality in future release.
 * This pertains to a future release functionality and should not be used in the current release.
 * @author Kyle Castillo
 *
 */
@Repository
public interface GoalRepository extends JpaRepository<Goal,Integer>{
	
	
	Goal findGoalById(int id);
	List<Goal> findGoalByGoalName(String name);
	List<Goal> findGoalByUserId(int id);
	List<Goal> findGoalByAccountId(int id);
	//TODO Implementing a query to find users before a given date.
	
	@Modifying
	@Query("update Goal g set g.goalAmount=:amount where g.id=:id")
	void updateGoalAmount(int id, int amount);
	
	@Modifying
	@Query("update Goal g set g.goalAmount=:amount where g.goalName =:goalName AND g.userId =:userId")
	void updateGoalAmount(String goalName, int userId, int amount);
	
	//TODO implementing setting the finish date for a goal.
}
