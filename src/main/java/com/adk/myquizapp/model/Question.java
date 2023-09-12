package com.adk.myquizapp.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int quesId;
	private String title;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private char ans;
	private char chose;
	@ManyToOne
	@JoinColumn(name = "technology_id")
	private Technology technology;

	public Question() {
		super();
	}


	public Question(int quesId, String title, String optionA, String optionB, String optionC,String optionD, char ans, char chose, Technology technology) {
		this.quesId = quesId;
		this.title = title;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.ans = ans;
		this.chose = chose;
		this.technology = technology;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public char getAns() {
		return ans;
	}

	public void setAns(char ans) {
		this.ans = ans;
	}

	public char getChose() {
		return chose;
	}

	public void setChose(char chose) {
		this.chose = chose;
	}

	@Override
	public String toString() {
		return "Question [quesId=" + quesId + ", title=" + title + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC + ", ans=" + ans + ", chose=" + chose + "]";
	}

}
