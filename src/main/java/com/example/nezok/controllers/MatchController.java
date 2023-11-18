package com.example.nezok.controllers;

import com.example.nezok.models.MeccsModel;
import com.example.nezok.repositories.MeccsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MatchController {

    @Autowired MeccsRepo meccsRepo;

    @GetMapping("/matches")
    public String getAll(Model model) {
        Iterable<MeccsModel> mecceses = meccsRepo.findAll();
        for(MeccsModel meccs : mecceses) {
            meccs.setDatum(meccs.getDatum().substring(0,9));
            meccs.setKezdes(meccs.getKezdes().substring(0,7));
        }
        model.addAttribute("matches", mecceses);
        model.addAttribute("pageTitle", "Admin| Match");
        return "/pages/match/index";
    }


}
