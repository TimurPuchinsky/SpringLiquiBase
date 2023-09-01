package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.language.LanguageModel;
import com.example.springliquidbase.domain.language.LanguagePageModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final DictionaryService dictionaryService;

    public LanguageModel getLanguageByName(String name) {
        var language = languageRepository.getLanguageByName(name);
        if (language == null) throw new NullPointerException();
        return language;
    }

    public GuidResultModel createLanguage(LanguageModel languageModel) {
        var searchLanguage = languageRepository.getLanguageByName(languageModel.getName());
        if (searchLanguage != null) {
            return new GuidResultModel("NullException", "язык уже существует");
        }
        var languageId = languageRepository.createNewLanguage(languageModel.getName());
        return new GuidResultModel(languageId);
    }

    public SuccessResultModel removeLanguage(String name) {
        var findLanguage = languageRepository.getLanguageByName(name);
        if (findLanguage == null) {
            return new SuccessResultModel("NullException", "такого языка нет");
        }
        var removeLanguage = languageRepository.removeLanguageByName(name);
        return new SuccessResultModel(removeLanguage);
    }

    public PageResultModel getPage(LanguagePageModel model) {
        return languageRepository.getPage(model);
    }

    public Map<UUID, List<LanguageModel>> getLanguagesByDictionaryIds(Map<UUID, DictionaryModel> dictionaries) {
        var languageIds = languageRepository.getLanguagesIdsListByDict(dictionaries);
        return languageRepository.getLanguagesListByDictionaryIds(languageIds);
    }
}