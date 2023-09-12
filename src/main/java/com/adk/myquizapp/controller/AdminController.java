package com.adk.myquizapp.controller;

import com.adk.myquizapp.model.Question;
import com.adk.myquizapp.model.Technology;
import com.adk.myquizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    QuizService quizService;


    @RequestMapping("/addQuestion")
    public String addQuestion(Model model)
    {
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        return "admin/addQuestion.html";
    }

    @RequestMapping("/addTechnology")
    public String addTech(Model model)
    {
        model.addAttribute("listOfTechnology",quizService.getAllTechnology());
        return "admin/addTechnology.html";
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
