package com.main.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.entity.User;
import com.main.repository.UserRepository;
import com.main.services.ProgressServices;

@Controller
public class ProgressController {

    @Autowired
    private ProgressServices pser;

    @Autowired
    private UserRepository urepo;

    @GetMapping("/progress")
    public String showProgress(Model model, Principal principal){

        User user = urepo.findByEmail(principal.getName()).get();

        model.addAttribute("total", pser.getTotalTasks(user));
        model.addAttribute("completed", pser.getCompletedTasks(user));
        model.addAttribute("pending", pser.getPendingTasks(user));
        model.addAttribute("progress", pser.getProgressPercentage(user));

        return "progress";
    }
}