package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domainservice.LanguageService;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/showAll")
    public Collection<LanguageModel> showLanguages(){
        return languageService.getAll();
    }

    @PostMapping("/add")
    public String addLanguages(@RequestBody LanguageModel languageModel){
        languageService.createLanguage(languageModel);
        return "add language";
    }
}
