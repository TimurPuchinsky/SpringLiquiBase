package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domainservice.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    private final DictionaryService dictionaryService;

    @PostMapping("/getPage")
    @Operation(summary = "список словарей")
    public PageResultModel getPage(@RequestBody DictionaryPageModel model) {
        return dictionaryService.getPage(model);
    }

    @PostMapping("/add")
    public UUID addDictionary(@RequestBody DictionaryModel dictionaryModel) {
        return dictionaryService.createDictionary(dictionaryModel);
    }
}