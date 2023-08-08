package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.Dictionary;
import com.example.springliquidbase.domain_service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("/dictionary")
@AllArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryServ;

//    @GetMapping("/show")
//    public Dictionary getDictionaries(){
//        return dictionaryServ.showAllDictionaries();
//    }

    @PostMapping("/add")
    public String addDictionary(@RequestParam Dictionary languageFrom, @RequestParam Dictionary languageTo){
        dictionaryServ.createDictionary(languageFrom, languageTo);
        return "Добавлен словарь";
    }
}
