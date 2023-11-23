package com.example.nezok.controllers;

import com.example.nezok.models.MeccsModel;
import com.example.nezok.repositories.MeccsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    MeccsRepo meccsRepo;

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "pages/home/index";
    }

    @GetMapping("learn-more")
    String learnMore(Model model) {
        model.addAttribute("pageTitle", "Home| Learn More");
        return "pages/home/learn-more";
    }

    @GetMapping("/matches")
    public String getAll(Model model) {
        model.addAttribute("matches", meccsRepo.findAll());
        model.addAttribute("pageTitle", "Admin| Match");
        return "pages/match/index";
    }


}
