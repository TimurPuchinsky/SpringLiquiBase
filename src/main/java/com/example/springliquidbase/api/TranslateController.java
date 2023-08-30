package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.StringResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.translate.TranslateAddModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domain.translate.TranslateResultModel;
import com.example.springliquidbase.domainservice.TranslateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public StringResultModel translation(@RequestParam String word, @RequestParam String languageFrom, @RequestParam String languageTo) {
        return translateService.getTranslate(word, languageFrom, languageTo);
    }

    @PostMapping("/getPage")
    @Operation(summary = "список переводов по словарю")
    public PageResultModel<TranslateResultModel> getPage(@RequestBody TranslatePageModel model) {
        return translateService.getPage(model);
    }

//    @GetMapping("/dictionary/{dictionary}")
//    public Collection<TranslateModel> dictionary(@PathVariable(name = "dictionary") String dictionaryName) {
//        return translateService.getDictionary(dictionaryName);
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public GuidResultModel addTranslate(@RequestBody TranslateAddModel translateModel){
        return translateService.createTranslate(translateModel);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change")
    public SuccessResultModel change(@RequestParam UUID translate_id, @RequestBody TranslateAddModel translateAddModel) {
        return translateService.changeTranslation(translate_id, translateAddModel);
    }
}