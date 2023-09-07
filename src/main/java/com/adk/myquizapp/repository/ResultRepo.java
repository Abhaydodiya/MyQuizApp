package com.adk.myquizapp.repository;

import com.adk.myquizapp.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {
	
}
