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

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    private final LanguageService languageService;

    @GetMapping("/languages")
    @ResponseBody
    public Collection<LanguageModel> languages(){
        return languageService.getAll();
    }

    @PostMapping("/add")
    @ResponseBody
    public UUID addLanguages(@RequestBody LanguageModel languageModel){
        return languageService.createLanguage(languageModel);
    }

    @PostMapping("/delete/{name}")
    @ResponseBody
    public int deleteLanguages(@PathVariable String name){
        return languageService.removeLanguage(name);
    }
}
