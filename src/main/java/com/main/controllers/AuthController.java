package com.main.controllers;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.main.entity.User;
import com.main.services.UserServiceImpl;

@Controller
public class AuthController {

    @Autowired
    private UserServiceImpl us;  
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user1", new User());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegisterForm(@ModelAttribute("user1") User user, Model model) {

        boolean status = us.registerUser(user);

        if (!status) {
            model.addAttribute("errormsg", "Email already exists!");
        } else {
            model.addAttribute("succMsg", "Registered Successfully!");
            model.addAttribute("user1", new User());
        }

        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

	
}
