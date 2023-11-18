package com.example.nezok.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin| Dashboard");
        return "/pages/admin/dashboard";
    }

}
