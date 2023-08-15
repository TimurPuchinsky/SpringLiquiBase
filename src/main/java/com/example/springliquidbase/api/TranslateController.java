package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.TranslateModel;
import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.domainservice.TranslateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/translate")
public class TranslateController {

    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    private final TranslateService translateService;

    @GetMapping("/translaion")
    @Operation(summary = "перевод слова")
    public TranslateModel showTranslate(@RequestParam String word, @RequestParam UUID dictionary) {
        return translateService.getTranslate(word, dictionary);
    }

    @GetMapping("/dictionary/{dictionary}")
    public Collection<TranslateModel> showDictionary(@PathVariable(name = "dictionary") UUID dictionaryId){
        return translateService.getDictionary(dictionaryId);
    }

    @PostMapping("/add")
    public String addTranslate(@RequestBody TranslateModel translateModel){
        return translateService.createTranslate(translateModel);
    }
}
