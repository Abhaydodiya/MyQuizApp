package com.adk.myquizapp.controller;

import com.adk.myquizapp.helper.Message;
import com.adk.myquizapp.model.User;
import com.adk.myquizapp.repository.UserRepository;
import com.adk.myquizapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Controller
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

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

    @RequestMapping("/signin/forgot")
    public String userPassForgetPage(Model model)
    {
        model.addAttribute("user",new User());
        return "forgot";
    }

    @PostMapping("/forget")
    public String verifyEmailForForgetAndSendOtp(@RequestParam("username") String email,Model model,HttpSession session)
    {
        Random random = new Random();
        int otp = random.nextInt(999999);
        String subject = "OTP From QuizWebApp";
        String message = "OTP = "+otp;
        boolean flag = this.emailService.sendEmail(message,subject,email);

        if(flag)
        {
            User user = new User();
            user.setEmail(email);
            model.addAttribute("user",user);
            session.setAttribute("otp",otp);
            return "/forgot";
        }
        else {
            User user = new User();
            user.setEmail(email);
            model.addAttribute("user",user);
            return "/forgot";
        }
    }

    @PostMapping("/otp")
    public String verifyOTP(@RequestParam("otp") int otp,HttpSession session,Model model)
    {
        if(otp == (int) session.getAttribute("otp"))
        {
            return "reset_password";
        }
        else {
            model.addAttribute("incorrect","Your Otp Incorrect, Please Try Again.");
            return "/forgot";
        }
    }

    @PostMapping("/new_pass")
    public String newPassVerify(@RequestParam("password") int pass,@RequestParam("re_password") int rePass,Model model)
    {
        if(pass!=rePass)
        {
            return "reset_password";
        }
        else {
            model.addAttribute("user",new User());
            return "login";
        }
    }
}
