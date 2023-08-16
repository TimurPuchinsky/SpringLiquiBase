package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domain.translate.TranslateModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domainservice.TranslateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/translate")
public class TranslateController {

    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    private final TranslateService translateService;

    @GetMapping("/translation")
    @Operation(summary = "перевод слова")
    public String translation(@RequestParam String word, @RequestParam String languageFrom, @RequestParam String languageTo) {
        return translateService.getTranslate(word, languageFrom, languageTo);
    }

    @PostMapping("/getPage")
    public PageResultModel getPage(@RequestBody TranslatePageModel model, @RequestParam String languageFrom, @RequestParam String languageTo) {
        return translateService.getPage(model, languageFrom, languageTo);
    }

    @GetMapping("/dictionary/{dictionary}")
    public Collection<TranslateModel> dictionary(@PathVariable(name = "dictionary") String dictionaryName) {
        return translateService.getDictionary(dictionaryName);
    }

    @PostMapping("/add")
    public UUID addTranslate(@RequestBody TranslateModel translateModel){
        return translateService.createTranslate(translateModel);
    }
}
