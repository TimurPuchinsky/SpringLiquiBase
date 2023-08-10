package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domainservice.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/language")
@AllArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/showAll")
    public List<LanguageModel> showLanguages(){
        return languageService.getAll();
    }

    @PostMapping("/add")
    public String addLanguages(@RequestBody LanguageModel languageModel){
        languageService.createLanguage(languageModel);
        return "add language";
    }
}
