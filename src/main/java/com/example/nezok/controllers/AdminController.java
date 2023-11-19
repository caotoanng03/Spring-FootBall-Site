package com.example.nezok.controllers;

import com.example.nezok.models.BelepesModel;
import com.example.nezok.models.MeccsModel;
import com.example.nezok.models.NezoModel;
import com.example.nezok.repositories.BelepesRepo;
import com.example.nezok.repositories.MeccsRepo;
import com.example.nezok.repositories.NezoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    @Autowired
    MeccsRepo meccsRepo;
    @Autowired
    NezoRepo nezoRepo;
    @Autowired
    BelepesRepo belepesRepo;

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

    @PostMapping("/admin/matches/createPost")
    public String createPost(@ModelAttribute MeccsModel meccsModel, RedirectAttributes redirAttr) {
        // check if the match already existed ?
        for (MeccsModel meccs : meccsRepo.findAll()) {
            if(meccs.getDatum().equals(meccsModel.getDatum()) && meccs.getKezdes().equals(meccsModel.getKezdes())) {
                redirAttr.addFlashAttribute("message", "Failed!This time is already reserved: "
                        + meccsModel.getDatum() + "; " + meccsModel.getKezdes() + ". Choose other time!");
                return "redirect:/admin/matches";
            }
        }
        // if not exists
        meccsRepo.save(meccsModel);
        redirAttr.addFlashAttribute("message", "New match was just inserted. ID=" + meccsModel.getId());
        return "redirect:/admin/matches";
    }

    @GetMapping("/admin/matches/edit/{id}")
    public String edit(@PathVariable(name="id") int id, Model model) {
        model.addAttribute("match", meccsRepo.findById(id));
        model.addAttribute("pageTitle", "Admin| Edit Match");
        return "/pages/admin/match/edit";
    }

    @PostMapping("/admin/matches/editPost")
    public String editPost(@ModelAttribute MeccsModel meccsModel, RedirectAttributes redirAttr) {
        int counter = 0;
        // check if the already existed > 2?
        for (MeccsModel meccs : meccsRepo.findAll()) {
            if(meccs.getDatum().equals(meccsModel.getDatum())
                    && meccs.getKezdes().equals(meccsModel.getKezdes())
                    && meccs.getId() != meccsModel.getId()) {
                ++counter;
            }
        }
        if(counter == 1 ) {
            redirAttr.addFlashAttribute("message", "Failed!This time is already reserved: "
                    + meccsModel.getDatum() + "; " + meccsModel.getKezdes() + ". Choose other time!");
            return "redirect:/admin/matches";
        }
        // if not existed
        meccsRepo.save(meccsModel);
        redirAttr.addFlashAttribute("message", "The employee just updated. ID=" + meccsModel.getId());
        return "redirect:/admin/matches";

    }

    @GetMapping("/admin/matches/delete/{id}")
    public String delete(@PathVariable(name="id") int id, RedirectAttributes redirAttr) {
        meccsRepo.deleteById(id);
        redirAttr.addFlashAttribute("message", "The match was deleted successfully. ID=" + id);
        return "redirect:/admin/matches";
    }

    @GetMapping("/admin/statistics")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Admin| Statistics");
        String str = display3Table();
        System.out.println(str);
        model.addAttribute("result", str);
        return "/pages/admin/statistic/index";
    }

//    String display3Table() {
//
//        String str = "";
//        int counterEntranceTime = 0;
//        int counterNumberOfNezo = 0;
//        int counterNumberOfNezoHasSeasonTicket = 0;
//
//        for(MeccsModel meccs : meccsRepo.findAll()) {
//            str += "Date: " + meccs.getDatum() + "; Type: " + meccs.getTipus();
//            for (BelepesModel belepes: belepesRepo.findAll()){
//               counterEntranceTime = 0;
//                if( meccs.getId() == belepes.getMeccsid()) {
//                    ++counterEntranceTime;
//                }
//
//                for (NezoModel nezo: nezoRepo.findAll()) {
//                    counterNumberOfNezo = 0;
//                    if(belepes.getNezoid() == nezo.getId()) {
//                        ++counterNumberOfNezo;
//                    }
//
//                    if(nezo.getBerletes() == 0) {
//                        ++counterNumberOfNezoHasSeasonTicket;
//                    }
//                    str += "Number of Watcher: " + counterNumberOfNezo;
//                    str += "Number of Watcher has season ticket: " + counterNumberOfNezoHasSeasonTicket;
//                    str += "<br>";
//                }
//
//                str += "Distinct Entrance Time: " + counterEntranceTime;
//                str += "<br>";
//            }
//
//            str += "<br><br>";
//
//        }
//
//        return str;
//    }

    String display3Table() {
        String str = "";
        str += "<tr><th>Date</th><th>Type</th><th>Number of Watchers</th><th>Watchers with Season Ticket</th><th>Distinct Entrance Times</th></tr>";

        for (MeccsModel meccs : meccsRepo.findAll()) {
            str += "<tr>";
            str += "<td>" + meccs.getDatum() + "</td><td>" + meccs.getTipus() + "</td>";

            Set<String> distinctEntranceTimes = new HashSet<>();
            int counterNumberOfNezo = 0;
            int counterNumberOfNezoHasSeasonTicket = 0;

            for (BelepesModel belepes : belepesRepo.findAll()) {
                if (meccs.getId() == belepes.getMeccsid()) {
                    distinctEntranceTimes.add(belepes.getIdopont());
                    ++counterNumberOfNezo;

                    for (NezoModel nezo : nezoRepo.findAll()) {
                        if (belepes.getNezoid() == nezo.getId()) {
                            if (nezo.getBerletes() == 0) {
                                ++counterNumberOfNezoHasSeasonTicket;
                            }
                        }
                    }
                }
            }

            str += "<td>" + counterNumberOfNezo + "</td>";
            str += "<td>" + counterNumberOfNezoHasSeasonTicket + "</td>";
            str += "<td>" + distinctEntranceTimes.size() + "</td>";
            str += "</tr>";
        }

        str += "";
        return str;
    }




}
