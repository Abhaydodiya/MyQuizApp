package com.adk.myquizapp.controller;

import java.util.List;

import com.adk.myquizapp.model.QuestionForm;
import com.adk.myquizapp.model.Result;
import com.adk.myquizapp.model.Technology;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
	
	@Autowired
	Result result;
	@Autowired
	QuizService qService;
	
	Boolean submitted = false;
	@ModelAttribute("result")
	public Result getResult() {

		return result;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("totalQuestions",qService.getQuestionsSize());
		model.addAttribute("limit","Note : Maximum "+qService.getQuestionsSize()+" number of questions allowed to enter.");
		model.addAttribute("listOfTechnology",qService.getAllTechnology());
		return "index.html";
	}
	
	@PostMapping("/quiz")
	public String quiz(@RequestParam String username, @RequestParam int numberOfQ, @RequestParam int technology, Model m, RedirectAttributes ra) {
		if(username.isEmpty()) {
			ra.addFlashAttribute("warning", "You must enter your name");
			return "redirect:/";
		}
		else if(numberOfQ>qService.getQuestionsSize() && numberOfQ<0)
		{
			ra.addFlashAttribute("limitWarning", "Please enter correct number of questions");
			return "redirect:/";
		}
		
		submitted = false;
		result.setUsername(username);
		Technology techById = qService.findTechnologyById(technology);

		QuestionForm qForm = qService.getQuestions(numberOfQ,techById);
		if(qForm.getQuestions().isEmpty())
		{
			ra.addFlashAttribute("languageDoesNotHaveQues", "Your selected language does not have any questions in our platform.");
			return "redirect:/";
		} else if (qForm.getQuestions().size()<numberOfQ) {
			m.addAttribute("doNotHaveEnoughQues", "We do not have enough question which you have entered technology, but we have "+ qForm.getQuestions().size() + " questions, that is there.");
		}
		m.addAttribute("qForm", qForm);
		return "quiz.html";
	}
	
	@PostMapping("/submit")
	public String submit(@ModelAttribute QuestionForm qForm, Model m) {
		if(!submitted) {
			result.setTotalCorrect(qService.getResult(qForm));
			qService.saveScore(result);
			submitted = true;
		}
		m.addAttribute("totalQues",qForm.getQuestions().size());
		return "result.html";
	}
	
	@GetMapping("/score")
	public String score(Model m) {
		List<Result> sList = qService.getTopScore();
		m.addAttribute("sList", sList);
		return "scoreboard.html";
	}

}
