package com.main.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.entity.User;
import com.main.repository.UserRepository;
import com.main.services.StreakService;

@Controller
public class StreakController {

    @Autowired
    private StreakService sser;

    @Autowired
    private UserRepository urepo;

    @GetMapping("/streak")
    public String showStreak(Model model, Principal principal){

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("currentStreak", sser.getCurrentStreak(user));
        model.addAttribute("longestStreak", sser.getLongestStreak(user));

        return "streak";
    }
}