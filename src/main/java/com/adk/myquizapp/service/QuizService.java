package com.adk.myquizapp.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.adk.myquizapp.model.*;
import com.adk.myquizapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
	
	@Autowired
	Question question;
	@Autowired
	QuestionForm qForm;
	@Autowired
	QuestionRepo qRepo;
	@Autowired
	TechnologyRepo tRepo;
	@Autowired
	Result result;
	@Autowired
	ResultRepo rRepo;
	@Autowired
	UserRepository uRepo;
	@Autowired
	QuizRepo quizRepo;

	
	public QuestionForm getQuestions(int size, Technology technology) {
		List<Question> qList = new ArrayList<Question>();
		Random random = new Random();

		List<Question> techWiseQues = qRepo.findQuestionsByTechnology(technology);
        if (!techWiseQues.isEmpty() && techWiseQues.size() >= size) {
            for (int i = 0; i < size; i++) {
                int rand = random.nextInt(techWiseQues.size());
                qList.add(techWiseQues.get(rand));
                techWiseQues.remove(rand);
            }
        } else if (techWiseQues.size() < size) {
			int sizeOfTechWiseQues = techWiseQues.size();
			for (int i = 0; i < sizeOfTechWiseQues; i++) {
				int rand = random.nextInt(techWiseQues.size());
				qList.add(techWiseQues.get(rand));
				techWiseQues.remove(rand);
			}
		}
		qForm.setQuestions(qList);
        return qForm;
    }

	public int getQuestionsSize()
	{
		return qRepo.findAll().size();
	}
	
	public int getResult(QuestionForm qForm) {
		int correct = 0;
		
		for(Question q: qForm.getQuestions())
			if(q.getAns() == q.getChose())
				correct++;
		
		return correct;
	}
	
	public void saveScore(Result result) {
		Result saveResult = new Result();
		saveResult.setUsername(result.getUsername());
		saveResult.setTotalCorrect(result.getTotalCorrect());
		rRepo.save(saveResult);
	}
	
	public List<Result> getTopScore() {
		List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
		return sList;
	}

	public void setTech(Technology technology)
	{
		tRepo.save(technology);
	}
	public void setQuestion(Question question)
	{
		qRepo.save(question);
	}

	public Technology findTechnologyById(int id)
	{
		return tRepo.findTechnologyByTechId(id);
	}
	public Technology findTechnologyByName(String name)
	{
		return tRepo.findTechnologyByTechnologyName(name);
	}

	public List<Technology> getAllTechnology()
	{
		return tRepo.findAll();
	}

	public User getUserByEmail(String email)
	{
		return uRepo.getUserByEmail(email);
	}

	public List<Quiz> findUpcomingQuizzes(LocalDateTime currentDateTime) {
		return quizRepo.findByScheduledDatetimeBefore(currentDateTime);
	}

	public void activateQuiz(Quiz quiz) {
		quiz.setActive(true);
		quizRepo.save(quiz);
	}


}
