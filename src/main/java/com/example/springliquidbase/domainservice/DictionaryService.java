package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryRepository;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final LanguageRepository languageRepository;

    public UUID createDictionary(DictionaryModel dictionaryModel) {
        return dictionaryRepository.createNewDictionary(dictionaryModel.getLanguageFrom(), dictionaryModel.getLanguageTo(), dictionaryModel.getName());
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
}