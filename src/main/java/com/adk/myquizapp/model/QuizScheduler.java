package com.adk.myquizapp.model;

import com.adk.myquizapp.repository.QuizRepo;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class QuizScheduler {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizRepo quizRepo;

    @Scheduled(fixedRate = 5000) // Runs every 5 sec (adjust as needed)
    public void updateQuizStatus() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Quiz> upcomingQuizzes = quizService.findUpcomingQuizzes(currentDateTime);

        for (Quiz quiz : upcomingQuizzes) {
            // Set the quiz as active when its scheduled time arrives
            quiz.setActive(true);
            quizRepo.save(quiz);
        }
    }
}
