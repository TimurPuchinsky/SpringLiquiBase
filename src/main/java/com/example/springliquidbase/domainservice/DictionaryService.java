package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.DictionaryModel;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryRepository;
import io.ebean.DataIntegrityException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public Collection<DictionaryModel> getAll() {
        return dictionaryRepository.findAll();
    }

    public String createDictionary(DictionaryModel dictionaryModel) {
        try {
            dictionaryRepository.createNewDictionary(dictionaryModel.getLanguageFrom(), dictionaryModel.getLanguageTo(), dictionaryModel.getName());
        } catch (DataIntegrityException e) {
            log.error(e.getMessage(), e);
            return "wrong language id";
        }
        return "dictionary added";
    }
}
