package com.main.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.entity.User;
import com.main.repository.UserRepository;
import com.main.services.AnalyticsService;

@Controller
public class AnalyticsController {

    @Autowired
    private AnalyticsService aser;

    @Autowired
    private UserRepository urepo;

    @GetMapping("/analytics")
    public String showAnalytics(Model model, Principal principal){

        User user = urepo.findByEmail(principal.getName()).get();

        /* Basic stats */
        model.addAttribute("total", aser.getTotalTasks(user));
        model.addAttribute("completed", aser.getCompletedTasks(user));
        model.addAttribute("pending", aser.getPendingTasks(user));
        model.addAttribute("rate", aser.getCompletionRate(user));

        /* Priority stats */
        model.addAttribute("high", aser.getHighPriority(user));
        model.addAttribute("medium", aser.getMediumPriority(user));
        model.addAttribute("low", aser.getLowPriority(user));

        /* Activity stats */
        model.addAttribute("today", aser.getTodayCompleted(user));
        model.addAttribute("weekly", aser.getWeeklyCompleted(user));

        return "analytics";
    }
}