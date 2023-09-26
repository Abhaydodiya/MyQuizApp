package com.adk.myquizapp.controller;

import com.adk.myquizapp.helper.Message;
import com.adk.myquizapp.model.User;
import com.adk.myquizapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/signin")
    public String userLoginPage(Model model) {
        model.addAttribute("title", "Sign in | Welcome to our shopping mart...");
//        model.addAttribute("login_line",model.getAttribute("login_line"));
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("/register")
    public String userRegisterPage(Model model) {
        model.addAttribute("title", "Sign up | Welcome to our shopping mart...");
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(path = "/user_registration", method = RequestMethod.POST)
    public String dataRegisterAndLoginPage(@ModelAttribute("user") User user, Model model, HttpSession session) {
        try {
            List<User> byEmailOrPhoneNumber = userRepository.findByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber());
            if(byEmailOrPhoneNumber.isEmpty())
            {
                user.setRole("ROLE_STUDENT");
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

                userRepository.save(user);
                session.setAttribute("message", new Message("Registration successfully now you login here", "alert-success"));
                return "login";
            }
            else if (byEmailOrPhoneNumber.iterator().hasNext()) {
                session.setAttribute("message", new Message("Something went wrong !! Your mobile number or email has been already registered so please go to login page.", "alert-danger"));
                return "register";
            } else {

                user.setRole("ROLE_STUDENT");
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

                userRepository.save(user);
                session.setAttribute("message", new Message("Registration successfully now you login here", "alert-success"));
                return "login";
            }
        } catch (Exception e) {
            session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "alert-danger"));
            return "register";
        }
    }

}
