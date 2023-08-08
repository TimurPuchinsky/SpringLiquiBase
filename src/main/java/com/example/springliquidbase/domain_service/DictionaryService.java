package com.example.springliquidbase.domain_service;

import com.example.springliquidbase.domain.Dictionary;
import com.example.springliquidbase.infrastructure.dictionaryRepository.DictionaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRep;
    public void createDictionary(Dictionary languageFrom, Dictionary languageTo) {
        //dictionaryRep.save(languageFrom, languageTo);
    }

//    public Dictionary showAllDictionaries() {
//        return dictionaryRep.getAll();
//    }
}
