package com.main.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.main.entity.User;
import com.main.repository.UserRepository;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private UserRepository urepo;

    @Autowired
    private BCryptPasswordEncoder encode;

    @GetMapping("")
    public String openSettingPage(Model model, Principal principal) {

        String email = principal.getName();
        User user = urepo.findByEmail(email).orElse(null);

        model.addAttribute("user", user);

        return "settings";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User formu, Principal principal) {

        User u = urepo.findByEmail(principal.getName()).orElse(null);

        if (u != null) {
            u.setName(formu.getName());
            u.setPhoneNo(formu.getPhoneNo());
            u.setDob(formu.getDob());
            u.setGender(formu.getGender());

            urepo.save(u);
        }

        return "redirect:/settings";
    }

    @PostMapping("/password")
    public String changePassword(@RequestParam String current,
                                 @RequestParam String newPass,
                                 Principal principal,
                                 Model model) {

        User user = urepo.findByEmail(principal.getName()).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        if (!encode.matches(current, user.getPassword())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Current password is incorrect");
            return "settings";
        }

        user.setPassword(encode.encode(newPass));
        urepo.save(user);

        return "redirect:/settings?success";
    }
}