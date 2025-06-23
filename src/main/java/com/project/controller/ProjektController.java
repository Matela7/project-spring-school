package com.project.controller;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import com.project.model.Projekt;
import com.project.service.ProjektService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Controller
public class ProjektController {
    private ProjektService projektService;
    //@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacji nie jest potrzebna
    @Autowired
    public ProjektController(ProjektService projektService) {
        this.projektService = projektService;
    }
    @GetMapping("/projektList")
    public String projektList(Model model, Pageable pageable, @RequestParam(name = "nazwa", required = false) String nazwa) {
        if (Strings.isNotBlank(nazwa)) {
            model.addAttribute("projekty", projektService.searchByNazwa(nazwa, pageable).getContent());
        } else {
            // Zamiast pustej listy, pobieraj projekty bezpośrednio z bazy danych
            model.addAttribute("projekty", projektService.getProjekty());
        }
        model.addAttribute("nazwa", nazwa);
        return "projektList";
    }
    @GetMapping("/projektEdit")
    public String projektEdit(@RequestParam(name="projektId", required = false) Integer projektId, Model model){
        if(projektId != null) {
            model.addAttribute("projekt", projektService.getProjekt(projektId).get());
        }else {
            Projekt projekt = new Projekt();
            model.addAttribute("projekt", projekt);
        }
        return "projektEdit";
    }
    @PostMapping(path = "/projektEdit")
    public String projektEditSave(@ModelAttribute @Valid Projekt projekt, BindingResult bindingResult) {
//parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
        if (bindingResult.hasErrors()) {
            return "projektEdit";
        }
        try {
            projekt = projektService.setProjekt(projekt);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(Strings.EMPTY, String.valueOf(e.getStatusCode().value()),
                    e.getStatusCode().toString());
            return "projektEdit";
        }
        return "redirect:/projektList";
    }
    @PostMapping(params="cancel", path = "/projektEdit")
    public String projektEditCancel() {
        return "redirect:/projektList";
    }
    @PostMapping(params="delete", path = "/projektEdit")
    public String projektEditDelete(@ModelAttribute Projekt projekt) {
        projektService.deleteProjekt(projekt.getProjektId());
        return "redirect:/projektList";
    }
}