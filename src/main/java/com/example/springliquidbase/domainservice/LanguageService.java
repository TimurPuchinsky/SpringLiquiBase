package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.Language;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
 import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<LanguageModel> getAll() {
        return languageRepository.findAll();
    }

    public void createLanguage(LanguageModel languageModel) {
        languageRepository.createNewLanguage(languageModel.getLanguage());
    }
}
