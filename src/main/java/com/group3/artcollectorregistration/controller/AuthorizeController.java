package com.group3.artcollectorregistration.controller;

import com.group3.artcollectorregistration.dto.ArtCollectorDto;
import com.group3.artcollectorregistration.entity.ArtCollector;
import com.group3.artcollectorregistration.service.ArtCollectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorizeController {

    private ArtCollectorService artCollectorService;

    @Autowired
    public AuthorizeController(ArtCollectorService artCollectorService) {
        this.artCollectorService = artCollectorService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerArtCollector(Model model) {
        ArtCollectorDto artCollector = new ArtCollectorDto();
        model.addAttribute("artCollector", artCollector);
//        return "register-art-collectors";
        return "registration-art-collectors";
    }

    @PostMapping("/register/save")
    public String saveArtCollector(@Valid @ModelAttribute("artCollector") ArtCollectorDto artCollector, BindingResult result, Model model) {
        ArtCollector existing = artCollectorService.findArtCollectorByEmail(artCollector.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "Account with this email already exists");
        }
        if (result.hasErrors()) {
            model.addAttribute("artCollector", artCollector);
//            return "register-art-collectors";
            return "registration-art-collectors";
        }
        artCollectorService.saveArtCollector(artCollector);
        return "redirect:/register?success";
    }


    @GetMapping("/artCollectors")
    public String listArtCollectors(Model model) {
        List<ArtCollector> artCollectors = artCollectorService.findAllArtCollector();
        model.addAttribute("artCollectors", artCollectors);
        return "art-collectors";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
