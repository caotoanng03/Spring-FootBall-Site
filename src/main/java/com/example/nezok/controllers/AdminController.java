package com.example.nezok.controllers;

import com.example.nezok.models.MeccsModel;
import com.example.nezok.repositories.MeccsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    @Autowired
    MeccsRepo meccsRepo;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin| Dashboard");
        return "/pages/admin/dashboard";
    }

    @GetMapping("/admin/matches")
    public String getAll(Model model) {
        model.addAttribute("matches", meccsRepo.findAll());
        model.addAttribute("pageTitle", "Home| Match");
        return "/pages/admin/match/index";
    }

    @GetMapping("/admin/matches/create")
    public String create(Model model) {
        model.addAttribute("match", new MeccsModel());
        model.addAttribute("pageTitle", "Admin| Match-Create");
        return "/pages/admin/match/create";
    }

    // [POST] /createPost
    @PostMapping("/admin/matches/createPost")
    public String createPost(@ModelAttribute MeccsModel meccsModel, RedirectAttributes redirAttr) {
        // check employee alreay existed ?
        for (MeccsModel meccs : meccsRepo.findAll()) {
            if(meccs.getDatum().equals(meccsModel.getDatum()) && meccs.getKezdes().equals(meccsModel.getKezdes())) {
                redirAttr.addFlashAttribute("message", "Failed!This time is already reserved. ID=" + meccs.getId());
                return "redirect:/admin/matches";
            }
        }
        // if not exists
        meccsRepo.save(meccsModel);
        redirAttr.addFlashAttribute("message", "New match was just inserted. ID=" + meccsModel.getId());
        return "redirect:/admin/matches";
    }


}
