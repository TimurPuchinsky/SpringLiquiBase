package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryRepository;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final LanguageRepository languageRepository;

    public GuidResultModel createDictionary(String languageFromString, String languageToString, String name) {
        var languageFrom = languageRepository.getLanguageByName(languageFromString);
        if (languageFrom == null) {
            return new GuidResultModel("NullException", "не нашелся язык");
        }
        var languageTo = languageRepository.getLanguageByName(languageToString);
        if (languageTo == null) {
            return new GuidResultModel("NullException", "не нашелся язык перевода");
        }
        return new GuidResultModel(dictionaryRepository.createNewDictionary(languageFrom.getId(), languageTo.getId(), name));
    }

    public DictionaryModel getDictionary(String name) {
        var dictionary = dictionaryRepository.find(name);
        if (dictionary == null) throw new NullPointerException();
        return dictionary;
    }

    public DictionaryModel getDictionaryByLanguages(String languageFrom, String languageTo) {
        var getLanguageFrom = languageRepository.getLanguageByName(languageFrom);
        if (getLanguageFrom == null) return null;
        var getLanguageTo = languageRepository.getLanguageByName(languageTo);
        if (getLanguageTo == null) return null;
        var dictionary = dictionaryRepository.getDictionaryLanguages(
                getLanguageFrom.getId(),
                getLanguageTo.getId());
        return dictionary;
    }

    public PageResultModel getPage(DictionaryPageModel model) {
        return dictionaryRepository.getPage(model);
    }

    public List<DictionaryModel> getAllDictionaries() {
        return dictionaryRepository.getAllDictionaries();
    }

    public DictionaryModel getDictionaryById(UUID dictionaryId) {
        return dictionaryRepository.getDictionaryById(dictionaryId);
    }
}