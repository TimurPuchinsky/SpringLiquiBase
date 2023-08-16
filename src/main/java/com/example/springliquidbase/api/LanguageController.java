package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.language.LanguageModel;
import com.example.springliquidbase.domainservice.LanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/language")
public class LanguageController {

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    private final LanguageService languageService;

    @GetMapping("/languages")
    public Collection<LanguageModel> languages(){
        return languageService.getAll();
    }

    @PostMapping("/add")
    public UUID addLanguages(@RequestBody LanguageModel languageModel){
        return languageService.createLanguage(languageModel);
    }

    @PostMapping("/delete/{name}")
    @ResponseBody
    public int deleteLanguages(@PathVariable String name){
        return languageService.removeLanguage(name);
    }
}
