package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.word.WordModel;
import com.example.springliquidbase.infrastructure.repository.wordrepository.WordRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final LanguageService languageService;

    public Collection<WordModel> getAll() {
        return wordRepository.findAll();
    }

    public int removeWord(String name) {
        return wordRepository.removeWordByName(name);
    }

    public UUID createWord(String word, String language) {
        return wordRepository.createNewWord(word, languageService.getLanguageByName(language).getId());
    }

    public WordModel getWordByName(String name) {
        var word = wordRepository.findWordByName(name);
        return word;
    }

    public WordModel getWordById(UUID uuid) {
        var word = wordRepository.findWordById(uuid);
        if (word == null) throw new NullPointerException();
        return word;
    }

    public Collection<WordModel> getWordByIds(Collection<UUID> ids) {
        var words = wordRepository.findWordsByIds(ids);
        if (words == null) throw new NullPointerException();
        return words;
    }
}