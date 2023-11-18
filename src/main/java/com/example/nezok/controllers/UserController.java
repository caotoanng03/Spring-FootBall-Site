package com.example.nezok.controllers;

import com.example.nezok.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/home")
    public String user() {
        return "/pages/user/user";
    }

}
