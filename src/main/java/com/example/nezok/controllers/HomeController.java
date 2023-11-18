package com.example.nezok.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
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


}
