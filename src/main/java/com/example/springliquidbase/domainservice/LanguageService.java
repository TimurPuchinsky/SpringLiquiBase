package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Collection<LanguageModel> getAll() {
        return languageRepository.findAll();
    }

    public String createLanguage(LanguageModel languageModel) {
        try {
            languageRepository.createNewLanguage(languageModel.getName());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "this language already exists";
        }
        return "language added";
    }

    public void removeLanguage(String name) {
        languageRepository.removeLanguageByName(name);
    }
}
