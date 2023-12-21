package com.registrationservice.controller;

import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import com.registrationservice.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @GetMapping("/user/profile")
    public String profile(Principal p, Model m){

        String email = p.getName();
        User user = userRepository.findByEmail(email);
        m.addAttribute("user",user);
        return "profile";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session){
        System.out.println("User Data-->> "+user);
        User newUser = userService.saveUser(user);
        if(newUser != null){
            System.out.println("New user Saved Successfully");
            session.setAttribute("msg","New User Saved Successfully");
        }
        else{
            System.out.println("Failed to save the User");
            session.setAttribute("msg","Something went wrong");
        }
        return "redirect:/register";

    }

}
