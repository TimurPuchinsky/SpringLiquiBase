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

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    private final WordService wordService;

    @GetMapping("/words")
    public Collection<WordModel> showWords(){
        return wordService.getAll();
    }

    @PostMapping("/add")
    public String addWord(@RequestParam String word, @RequestParam String language){
        return wordService.createWord(word, language);
    }

    @PostMapping("/delete/{name}")
    public String deleteWord(@PathVariable String name){
        wordService.removeWord(name);
        return "word removed";
    }
}
