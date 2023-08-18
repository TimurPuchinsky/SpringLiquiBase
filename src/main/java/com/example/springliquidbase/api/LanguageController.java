package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.StringResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.language.LanguageModel;
import com.example.springliquidbase.domain.language.LanguagePageModel;
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

    @PostMapping("/languages")
    public PageResultModel languages(@RequestBody LanguagePageModel model) {
        return languageService.getPage(model);
    }

    @PostMapping("/add")
    public GuidResultModel addLanguages(@RequestBody LanguageModel languageModel) {
        return languageService.createLanguage(languageModel);
    }

    @PostMapping("/delete/{name}")
    public SuccessResultModel deleteLanguages(@PathVariable String name) {
        return languageService.removeLanguage(name);
    }
}
