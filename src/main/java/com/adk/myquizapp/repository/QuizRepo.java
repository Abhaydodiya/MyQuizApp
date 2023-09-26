package com.adk.myquizapp.repository;


import com.adk.myquizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz,Integer> {

    public Quiz findQuizByQuizCode(int quizCode);
    List<Quiz> findByScheduledDatetimeBefore(LocalDateTime currentDateTime);

}
