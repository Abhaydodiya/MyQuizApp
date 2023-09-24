package com.adk.myquizapp.controller;

import com.adk.myquizapp.model.User;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    QuizService quizService;

    public User getUser(Principal principal) {
        String user = principal.getName();
        return quizService.getUserByEmail(user);
    }
    @GetMapping("admin")
    public String dashboard(Model model,Principal principal)
    {
        model.addAttribute("name",getUser(principal).getName());
        return "admin/dashboard";
    }
}
