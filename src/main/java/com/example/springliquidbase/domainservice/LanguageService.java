package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Collection<LanguageModel> getAll() {
        return languageRepository.findAll();
    }

    public void createLanguage(LanguageModel languageModel) {
        languageRepository.createNewLanguage(languageModel.getName());
    }

    public void removeLanguage(String name) {
        languageRepository.removeLanguageByName(name);
    }
}
