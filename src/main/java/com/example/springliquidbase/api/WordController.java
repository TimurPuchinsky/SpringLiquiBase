package com.example.springliquidbase.api;

import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.domainservice.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/words")
    public Collection<WordModel> showWords(){
        return wordService.getAll();
    }

    @PostMapping("/add")
    public String addWord(@RequestBody WordModel wordModel){
        return wordService.createWord(wordModel);
    }

    @PostMapping("/delete/{name}")
    public String deleteWord(@PathVariable String name){
        wordService.removeWord(name);
        return "word removed";
    }

}
