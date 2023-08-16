package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.MyCustomException;
import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.infrastructure.repository.wordrepository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final LanguageService languageService;

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<String> handleException(MyCustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    public Collection<WordModel> getAll() {
        return wordRepository.findAll();
    }

    public int removeWord(String name) {
        return wordRepository.removeWordByName(name);
    }

    public UUID createWord(String word, String language) {
        try {
            return wordRepository.createNewWord(word, languageService.getLanguageByName(language).getId());
        } catch (MyCustomException e) {
            throw e;
        } catch (Exception e) {
            throw new MyCustomException("Error creating word");
        }
    }

    public WordModel getWordByName(String name) {
        var word = wordRepository.findWordByName(name);
        if (word == null) throw new NullPointerException();
        return word;
    }

    public WordModel getWordById(UUID name) {
        var word = wordRepository.findWordById(name);
        if (word == null) throw new NullPointerException();
        return word;
    }
}