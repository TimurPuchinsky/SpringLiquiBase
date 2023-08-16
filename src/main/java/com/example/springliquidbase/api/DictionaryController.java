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

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    private final DictionaryService dictionaryService;

//    @GetMapping("/dictionaries")
//    @ResponseBody
//    public Collection<DictionaryModel> showDictionaries() {
//        return dictionaryService.getAll();
//    }
    @PostMapping("/getPage")
    public PageResultModel getPage(@RequestBody DictionaryPageModel model) {
        return dictionaryService.getPage(model);
    }

    @PostMapping("/add")
    @ResponseBody
    public UUID addDictionary(@RequestBody DictionaryModel dictionaryModel) {
        return dictionaryService.createDictionary(dictionaryModel);
    }
}
