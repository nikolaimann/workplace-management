package de.ehex.workplacemanagment;

import ch.qos.logback.core.encoder.EchoEncoder;
import de.ehex.workplacemanagment.buchungen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@SuppressWarnings("unused")
public class WebController {

    @Autowired
    BuchungRepository buchungRepository;

    @Autowired
    BuchungController controller;

    @GetMapping("/")
    public String index(Model model) {
        List<Buchung> buchungen = new ArrayList<>(buchungRepository.findAll());
        model.addAttribute("buchungen", buchungen);
        return "index";
    }

    @GetMapping("/buchung/buchen")
    public String buchen(Model model) {
        model.addAttribute(new CreateBuchung());
        return "buchen";
    }

    @PostMapping("/buchung/buchen")
    public String buchungAbschicken(@ModelAttribute CreateBuchung createBuchung, Model model) {

        try {
            controller.newBuchung((createBuchung));
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "buchen";
        }
        List<Buchung> buchungen = new ArrayList<>(buchungRepository.findAll());
        model.addAttribute("buchungen", buchungen);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
