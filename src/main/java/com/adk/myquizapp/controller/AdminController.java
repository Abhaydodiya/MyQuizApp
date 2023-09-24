package com.adk.myquizapp.controller;

import com.adk.myquizapp.model.Question;
import com.adk.myquizapp.model.Result;
import com.adk.myquizapp.model.Technology;
import com.adk.myquizapp.model.User;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    QuizService quizService;

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
}
