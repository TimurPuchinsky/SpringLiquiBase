package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domainservice.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/language")
public class LanguageController {

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    private final LanguageService languageService;

    @GetMapping("/languages")
    public Collection<LanguageModel> showLanguages(){
        return languageService.getAll();
    }

    @PostMapping("/add")
    public String addLanguages(@RequestBody LanguageModel languageModel){
        return languageService.createLanguage(languageModel);
    }

    @PostMapping("/delete/{name}")
    public String deleteLanguages(@PathVariable String name){
        languageService.removeLanguage(name);
        return "language removed";
    }
}
