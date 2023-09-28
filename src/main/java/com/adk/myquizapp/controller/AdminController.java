package com.adk.myquizapp.controller;

import com.adk.myquizapp.model.*;
import com.adk.myquizapp.repository.QuestionRepo;
import com.adk.myquizapp.repository.QuizRepo;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    QuizService quizService;
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;

    public User getUser(Principal principal) {
        String user = principal.getName();
        return quizService.getUserByEmail(user);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,Principal principal)
    {
        model.addAttribute("name",getUser(principal).getName());
        return "admin/dashboard";
    }

    @RequestMapping("/addQuestion")
    public String addQuestion(Model model,Principal principal)
    {
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        model.addAttribute("name",getUser(principal).getName());

        return "admin/addQuestion";
    }

    @GetMapping("/questions")
    public String questions(Model model,Principal principal)
    {
        model.addAttribute("questions",questionRepo.findAll());
        model.addAttribute("name",getUser(principal).getName());

        return "admin/questions";
    }

    @GetMapping("/technologies")
    public String technologies(Model model,Principal principal)
    {
        model.addAttribute("technologies",quizService.getAllTechnology());
        model.addAttribute("name",getUser(principal).getName());

        return "admin/technologies";
    }

    @GetMapping("/scoreboard")
    public String scoreboard(Model m)
    {
        List<Result> sList = quizService.getTopScore();
        m.addAttribute("sList", sList);
        m.addAttribute("admin","admin");
        return "user/scoreboard";
    }

    @RequestMapping("/addTechnology")
    public String addTech(Model model,Principal principal)
    {
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        model.addAttribute("name",getUser(principal).getName());
        return "admin/addTechnology";
    }

    @RequestMapping(value = "/submitQuestion",method = RequestMethod.POST)
    public String submitQues(@ModelAttribute Question question, Model model)
    {
        question.setChose('-');
        quizService.setQuestion(question);
        return "redirect:/admin/addQuestion";
    }

    @RequestMapping(value = "/submitTechnology",method = RequestMethod.POST)
    public String submitTech(@ModelAttribute Technology technology, Model model)
    {
        quizService.setTech(technology);
        return "redirect:/admin/addQuestion";
    }

    @GetMapping(value = "/quizGenerate")
    public String generateQuiz(Model model)
    {
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        return "admin/quizGenerate";
    }

    @PostMapping(value = "/generate-quiz")
    public String submitQuizSchedule(@ModelAttribute Quiz q,@RequestParam("schedule") String schedule,Model model)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime scheduledDateTime = LocalDateTime.parse(schedule, formatter);
        q.setScheduledDatetime(scheduledDateTime);
        q.setActive(false);
        quizRepo.save(q);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/question/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable(value = "id") int id)
    {
        quizService.deleteQuestionById(id);
        return "redirect:/admin/questions";
    }

    @GetMapping("/technology/deleteTechnology/{id}")
    public String deleteTechnology(@PathVariable(value = "id") int id)
    {
        quizService.deleteTechById(id);
        return "redirect:/admin/technologies";
    }

}
