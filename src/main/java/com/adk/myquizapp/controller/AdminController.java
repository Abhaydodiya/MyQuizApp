package com.adk.myquizapp.controller;

import com.adk.myquizapp.model.*;
import com.adk.myquizapp.repository.QuestionRepo;
import com.adk.myquizapp.repository.QuizRepo;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    QuizService quizService;
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    QuizScheduler quizScheduler;

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

    @GetMapping("/quizSchedule")
    public String quizSchedule(Model m)
    {
        quizScheduler.updateQuizStatus();
        List<Quiz> quizList = quizService.quizList();
        m.addAttribute("quizList", quizList);
        m.addAttribute("admin","admin");
        return "admin/quizSchedules";
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
        Random random = new Random();
        int i = random.nextInt(100000,999999);
        q.setQuizCode(i);
        quizRepo.save(q);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/question/updateQuestion/{id}")
    public String updateQuestionPage(@PathVariable(value = "id") int id,Model model)
    {
        Question questionById = quizService.findQuestionById(id);
        model.addAttribute("question",questionById);
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        return "admin/updateQuestion";
    }
    @GetMapping("/technology/updateTechnology/{id}")
    public String updateTechnologyPage(@PathVariable(value = "id") int id,Model model)
    {
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        model.addAttribute("tech",quizService.findTechnologyById(id));
        return "admin/updateTechnology";
    }
    @PostMapping("/updateQuestion")
    public String updateQuestion(@ModelAttribute("question")Question question,Model model)
    {
        questionRepo.save(question);
        return "redirect:/admin/questions";
    }
    @PostMapping("/updateTechnology")
    public String updateTechnology(@ModelAttribute("technology") Technology technology,Model model)
    {
        quizService.setTech(technology);
        return "redirect:/admin/technologies";
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

    @GetMapping("/quizSchedules/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable(value = "id") int id)
    {
        quizService.deleteQuiz(id);
        return "redirect:/admin/quizSchedule";
    }
}
