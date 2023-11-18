package com.example.nezok.controllers;

import com.example.nezok.models.ContactModel;
import com.example.nezok.repositories.ContactRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class ContactController {

    @Autowired
    ContactRepo contactRepo;

    @GetMapping("/contact")
    public String form(Model model) {
        model.addAttribute("contactModel", new ContactModel());
        return "pages/contact/form";
    }

    @PostMapping("/contact")
    public String postForm(@Valid @ModelAttribute ContactModel contactModel,
                           BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "pages/contact/form";
        }
        contactModel.setDate(new Date());
        contactRepo.save(contactModel);
        model.addAttribute("formResult", contactModel);
        return "pages/contact/formResult";
    }

}
