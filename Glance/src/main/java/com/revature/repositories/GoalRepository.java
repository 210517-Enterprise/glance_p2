package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.entities.Goal;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Integer>{
	
}
