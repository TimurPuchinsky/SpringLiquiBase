package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.TranslateModel;
import com.example.springliquidbase.infrastructure.repository.translaterepository.TranslateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class TranslateService {

    @Autowired
    public TranslateService(TranslateRepository translateRepository) {
        this.translateRepository = translateRepository;
    }

    private final TranslateRepository translateRepository;

    public TranslateModel getTranslate(String word, UUID dictionary) {
        TranslateModel translateModel = translateRepository.getTranslateByWord(word, dictionary);
        if (translateModel == null) throw new NullPointerException();
        return translateModel;
    }

    public Collection<TranslateModel> getDictionary(UUID dictionaryId) {
        return translateRepository.getDictionaryById(dictionaryId);
    }

    public String createTranslate(TranslateModel translateModel) {
        translateRepository.createNewTranslate(translateModel.getWordModelFrom(),
                translateModel.getWordModelTo(),
                translateModel.getDictionary());
        return "translate added";
    }
}
