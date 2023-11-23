package com.example.nezok.controllers;

import com.example.nezok.models.*;
import com.example.nezok.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class AdminController {
    @Autowired
    MeccsRepo meccsRepo;
    @Autowired
    NezoRepo nezoRepo;
    @Autowired
    BelepesRepo belepesRepo;
    @Autowired
    ContactRepo contactRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin| Dashboard");
        return "pages/admin/dashboard";
    }

    @GetMapping("/admin/matches")
    public String getAll(Model model) {
        model.addAttribute("matches", meccsRepo.findAll());
        model.addAttribute("pageTitle", "Home| Match");
        return "pages/admin/match/index";
    }

    @GetMapping("/admin/matches/create")
    public String create(Model model) {
        model.addAttribute("match", new MeccsModel());
        model.addAttribute("pageTitle", "Admin| Match-Create");
        return "pages/admin/match/create";
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
        return "pages/admin/match/edit";
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
    public String display3TableInfo(Model model) {
        model.addAttribute("pageTitle", "Admin| Statistics");
        String str = display3Table();
        model.addAttribute("result", str);
        return "pages/admin/statistic/index";
    }

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

    @GetMapping("/admin/watchers")
    public String getAllWatcherDetails(Model model) {
        model.addAttribute("pageTitle", "Admin| Watcher");
        model.addAttribute("watchers", nezoRepo.findAll());
        return "pages/admin/watcher/index";
    }

    @GetMapping("/admin/messages")
    public String getClientMessages(Model model) {
        model.addAttribute("pageTitle", "Admin| Messages");
        String str = getClientMessage();
        model.addAttribute("message", str);
        return "pages/admin/messages/index";
    }

    String getClientMessage() {
        String str = "<table class=\"table\"> " +
                "<thead class=\"table-light\">\n" +
                "      <tr>\n" +
                "        <th >ID</th>\n" +
                "        <th >Email</th>\n" +
                "        <th >Content</th>\n" +
                "        <th >Sending Date</th>\n" +
                "        <th >Who sent?</th>\n" +
                "      </tr>\n" +
                "      </thead><tbody>";
        ArrayList<ContactModel> messageList = (ArrayList<ContactModel>) contactRepo.findAll();
        Collections.sort(messageList, (ContactModel obj1, ContactModel obj2) -> {
            return obj1.getEmail().compareToIgnoreCase(obj2.getEmail());
        });
        for(ContactModel message: messageList) {
            str += "<tr>";
            str += "<td>" + message.getId() + "</td>";
            str += "<td>" + message.getEmail() + "</td>";
            str += "<td>" + message.getContent() + "</td>";
            str += "<td>" + message.getDate() + "</td>";
            boolean isUser = false;
            for(UserModel user : userRepo.findAll()) {
                if (user.getEmail().equals(message.getEmail())) {
                    isUser = true;
                    break;
                }
            }
            if(isUser) {
                str += "<td> User </td>";
            } else {
                str += "<td> Guest </td>";
            }
            str += "</tr>";
        }
        str += "</tbody></table>";
        return str;
    }




}
