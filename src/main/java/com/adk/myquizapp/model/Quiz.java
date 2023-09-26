package com.adk.myquizapp.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizId;

    private int numberOfQuestions;
    @Column(unique = true)
    private int quizCode;
    private String technology;
    private LocalDateTime scheduledDatetime;
    private boolean isActive;

    public Quiz() {
    }

    public Quiz(int quizId, int numberOfQuestions, int quizCode, String technology, LocalDateTime scheduledDatetime, boolean isActive) {
        this.quizId = quizId;
        this.numberOfQuestions = numberOfQuestions;
        this.quizCode = quizCode;
        this.technology = technology;
        this.scheduledDatetime = scheduledDatetime;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getQuizCode() {
        return quizCode;
    }

    public void setQuizCode(int quizCode) {
        this.quizCode = quizCode;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public LocalDateTime getScheduledDatetime() {
        return scheduledDatetime;
    }

    public void setScheduledDatetime(LocalDateTime scheduledDatetime) {
        this.scheduledDatetime = scheduledDatetime;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", numberOfQuestions=" + numberOfQuestions +
                ", quizCode=" + quizCode +
                ", technology='" + technology + '\'' +
                ", scheduledDatetime=" + scheduledDatetime +
                ", isActive=" + isActive +
                '}';
    }
}

