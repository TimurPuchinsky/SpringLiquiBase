package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.infrastructure.repository.wordrepository.WordRepository;
import io.ebean.DataIntegrityException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class WordService {

    private final WordRepository wordRepository;

    public Collection<WordModel> getAll() {
        return wordRepository.findAll();
    }

    public void removeWord(String name) {
        wordRepository.removeWordByName(name);
    }

    public String createWord(String word, String language) {
        try {
            wordRepository.createNewWord(word, language);
        } catch (DataIntegrityException e) {
            log.error(e.getMessage(), e);
            return "wrong language was selected";
        }
        return "word added";
    }
}