package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.MyCustomException;
import com.example.springliquidbase.domain.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<String> handleException(MyCustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    public Collection<DictionaryModel> getAll() {
        return dictionaryRepository.findAll();
    }

    public UUID createDictionary(DictionaryModel dictionaryModel) {
        return dictionaryRepository.createNewDictionary(dictionaryModel.getLanguageFrom(), dictionaryModel.getLanguageTo(), dictionaryModel.getName());
    }

    public DictionaryModel getDictionary(String name) {
        var dictionary = dictionaryRepository.find(name);
        if (dictionary == null) throw new NullPointerException();
        return dictionary;
    }

    public PageResultModel getPage(DictionaryPageModel model) {
        return dictionaryRepository.getPage(model);
    }
}
