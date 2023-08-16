package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domainservice.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    private final DictionaryService dictionaryService;

    @PostMapping("/getPage")
    public PageResultModel getPage(@RequestBody DictionaryPageModel model) {
        return dictionaryService.getPage(model);
    }

    @PostMapping("/add")
    public UUID addDictionary(@RequestBody DictionaryModel dictionaryModel) {
        return dictionaryService.createDictionary(dictionaryModel);
    }
}
