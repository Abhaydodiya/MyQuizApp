package com.adk.myquizapp.repository;

import com.adk.myquizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    public ArrayList<Question> findQuestionsByTechnology(String technology);
}