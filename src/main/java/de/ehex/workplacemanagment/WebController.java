package de.ehex.workplacemanagment;

import de.ehex.workplacemanagment.buchungen.*;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SuppressWarnings("unused")
public class WebController {

    @Autowired
    BuchungRepository buchungRepository;

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

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
    public String buchungAbschicken(@ModelAttribute CreateBuchung createBuchung, @CurrentSecurityContext(expression="authentication.name")
            String benutzername, Model model) {
        try {
            controller.newBuchung(createBuchung, benutzername);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "buchen";
        }
        List<Buchung> buchungen = new ArrayList<>(buchungRepository.findAll());
        model.addAttribute("buchungen", buchungen);
        return "index";
    }

    @GetMapping("/buchung/meineBuchungen")
    public String meineBuchungen(@CurrentSecurityContext(expression="authentication.name") String benutzername, Model model) {
        model.addAttribute("buchungen", buchungRepository.findByMitarbeiterId(mitarbeiterRepository.findMitarbeiterByBenutzername(benutzername).getId()));
        return "meineBuchungen";
    }

    @GetMapping("/buchung/delete")
    public String buchungLoeschen(@RequestParam(name="id") long id , @CurrentSecurityContext(expression="authentication.name") String benutzername, Model model) {
        controller.deleteBuchung(id);
        model.addAttribute("buchungen", buchungRepository.findByMitarbeiterId(mitarbeiterRepository.findMitarbeiterByBenutzername(benutzername).getId()));
        return "meineBuchungen";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
