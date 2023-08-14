package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domainservice.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/languages")
    public Collection<LanguageModel> showLanguages(){
        return languageService.getAll();
    }

    @PostMapping("/add")
    public String addLanguages(@RequestBody LanguageModel languageModel){
        languageService.createLanguage(languageModel);
        return "language added";
    }

    @PostMapping("/delete/{name}")
    public String deleteLanguages(@PathVariable String name){
        languageService.removeLanguage(name);
        return "language removed";
    }
}
