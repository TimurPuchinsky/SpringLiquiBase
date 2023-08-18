package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.StringResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.word.WordPageModel;
import com.example.springliquidbase.domainservice.WordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word")
public class WordController {

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    private final WordService wordService;

    @PostMapping("/getPage")
    public PageResultModel getPage(@RequestBody WordPageModel model){
        return wordService.getPage(model);
    }

    @PostMapping("/add")
    public GuidResultModel addWord(@RequestParam String word, @RequestParam String language){
        return wordService.createWord(word, language);
    }

    @PostMapping("/delete/{name}")
    public SuccessResultModel deleteWord(@PathVariable String name){
        return wordService.removeWord(name);
    }
}
