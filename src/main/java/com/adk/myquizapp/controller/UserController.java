package com.adk.myquizapp.controller;

import com.adk.myquizapp.model.*;
import com.adk.myquizapp.repository.QuizRepo;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    Result result;
    @Autowired
    QuizService qService;
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    QuizScheduler quizScheduler;

    Boolean submitted = false;

    @ModelAttribute("result")
    public Result getResult() {

        return result;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String email = principal.getName();
        User userByEmail = qService.getUserByEmail(email);
        model.addAttribute("totalQuestions", qService.getQuestionsSize());
        model.addAttribute("limit", "Note : Maximum " + qService.getQuestionsSize() + " number of questions allowed to enter.");
        model.addAttribute("listOfTechnology", qService.getAllTechnology());
        model.addAttribute("welcome", "Welcome, " + userByEmail.getName());
        model.addAttribute("name", userByEmail.getName());
        return "user/index";
    }

    @PostMapping("/quiz")
    public String quiz(@RequestParam String username, @RequestParam int numberOfQ, @RequestParam int technology, Model m, RedirectAttributes ra) {
        if (username.isEmpty()) {
            ra.addFlashAttribute("warning", "You must enter your name");
            return "redirect:user/home";
        } else if (numberOfQ > qService.getQuestionsSize() && numberOfQ < 0) {
            ra.addFlashAttribute("limitWarning", "Please enter correct number of questions");
            return "redirect:user/home";
        }

        submitted = false;
        result.setUsername(username);
        Technology techById = qService.findTechnologyById(technology);

        QuestionForm qForm = qService.getQuestions(numberOfQ, techById);
        if (qForm.getQuestions().isEmpty()) {
            ra.addFlashAttribute("languageDoesNotHaveQues", "Your selected language does not have any questions in our platform.");
            return "redirect:user/home";
        } else if (qForm.getQuestions().size() < numberOfQ) {
            m.addAttribute("doNotHaveEnoughQues", "We do not have enough question which you have entered technology, but we have " + qForm.getQuestions().size() + " questions, that is there.");
        }
        m.addAttribute("qForm", qForm);
        return "user/quiz";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute QuestionForm qForm, Model m) {
        if (!submitted) {
            result.setTotalCorrect(qService.getResult(qForm));
            qService.saveScore(result);
            submitted = true;
        }
        m.addAttribute("totalQues", qForm.getQuestions().size());
        return "user/result";
    }

    @GetMapping("/score")
    public String score(Model m) {
        List<Result> sList = qService.getTopScore();
        m.addAttribute("sList", sList);
        return "user/scoreboard";
    }

    @PostMapping("/code_quiz")
    public String codeQuiz(@RequestParam("username") String username, @RequestParam("quizCode") int quizCode, Model m, RedirectAttributes ra) {
        submitted = false;
        result.setUsername(username);
        try {
            Quiz quizByQuizCode = quizRepo.findQuizByQuizCode(quizCode);
            quizScheduler.updateQuizStatus();
            if (quizByQuizCode.isActive()) {
                Technology techById = qService.findTechnologyById(qService.findTechnologyByName(quizByQuizCode.getTechnology()).getTechId());

                QuestionForm qForm = qService.getQuestions(quizByQuizCode.getNumberOfQuestions(), techById);
                if (qForm.getQuestions().isEmpty()) {
                    ra.addFlashAttribute("languageDoesNotHaveQues", "Your selected language does not have any questions in our platform.");
                    return "redirect:user/home";
                } else if (qForm.getQuestions().size() < quizByQuizCode.getNumberOfQuestions()) {
                    m.addAttribute("doNotHaveEnoughQues", "We do not have enough question which you have entered technology, but we have " + qForm.getQuestions().size() + " questions, that is there.");
                }
                m.addAttribute("qForm", qForm);
                return "user/quiz";
            } else {
                return "user/index";
            }
        } catch (Exception e)
        {
            return "user/index";
        }
    }
}
