package com.adk.myquizapp.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Table(name = "technology")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int techId;
    private String technologyName;
    @OneToMany(mappedBy = "technology")
    private List<Question> questionList;

    public Technology() {
    }
    public Technology(int techId, String technologyName, List<Question> questionList) {
        this.techId = techId;
        this.technologyName = technologyName;
        this.questionList = questionList;
    }

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public int getQuestionSize()
    {
        return questionList.size();
    }
    @Override
    public String toString() {
        return "Technology{" +
                "techId=" + techId +
                ", technologyName='" + technologyName + '\'' +
                ", questionList=" + questionList +
                ", questionSize=" + getQuestionSize() +
                '}';
    }
}
