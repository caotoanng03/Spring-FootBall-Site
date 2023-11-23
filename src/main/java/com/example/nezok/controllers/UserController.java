package com.example.nezok.controllers;

import com.example.nezok.models.UserModel;
import com.example.nezok.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("data", new UserModel());
        model.addAttribute("pageTitle", "Home| Register");
        return "pages/user/register";
    }

    @PostMapping("/registerPost")
    public String registerPost(@ModelAttribute UserModel user, Model model) {
        for(UserModel user1 : userRepo.findAll()) {
            if(user1.getEmail().equals(user.getEmail())) {
                model.addAttribute("errorMessage", "Email already existed");
                return "pages/user/register-error";
            }
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
        model.addAttribute("id", user.getId());
        return "pages/user/register-success";
    }

}
