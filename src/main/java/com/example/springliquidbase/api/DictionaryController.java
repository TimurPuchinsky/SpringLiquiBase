package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.DictionaryModel;
import com.example.springliquidbase.domainservice.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    private final DictionaryService dictionaryService;

    @GetMapping("/dictionaries")
    public Collection<DictionaryModel> showDictionaries() {
        return dictionaryService.getAll();
    }

    @PostMapping("/add")
    public String addDictionary(@RequestBody DictionaryModel dictionaryModel) {
        return dictionaryService.createDictionary(dictionaryModel);
    }
}
