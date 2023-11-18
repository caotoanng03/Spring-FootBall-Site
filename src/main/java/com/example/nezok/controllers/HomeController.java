package com.example.nezok.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("title", "Home");
        return "pages/home/index";
    }

    @GetMapping("/test")
    String test(Model model) {
        return "test";
    }
}
