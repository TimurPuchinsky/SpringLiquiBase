package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.MyCustomException;
import com.example.springliquidbase.domain.language.LanguageModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<String> handleException(MyCustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    public Collection<LanguageModel> getAll() {
        return languageRepository.findAll();
    }

    public LanguageModel getLanguageByName(String name) {
        var language = languageRepository.find(name);
        if (language == null) throw new NullPointerException();
        return language;
    }

    public UUID createLanguage(LanguageModel languageModel) {
        UUID languageId = languageRepository.createNewLanguage(languageModel.getName());
        if (languageId == null) throw new NullPointerException();
        return languageId;
    }

    public int removeLanguage(String name) {
        return languageRepository.removeLanguageByName(name);
    }
}