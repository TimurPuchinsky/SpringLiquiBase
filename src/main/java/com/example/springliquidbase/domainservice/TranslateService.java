package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.MyCustomException;
import com.example.springliquidbase.domain.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domain.translate.TranslateModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.infrastructure.repository.translaterepository.TranslateRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TranslateService {

    private final TranslateRepository translateRepository;
    private final DictionaryService dictionaryService;
    private final LanguageService languageService;
    private final WordService wordService;

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<String> handleException(MyCustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    public String getTranslate(String word, String languageTo) {
        try {
            UUID translate = translateRepository.getTranslateByWord(
                    wordService.getWordByName(word).getId(),
                    languageService.getLanguageByName(languageTo).getId());
            return wordService.getWordById(translate).getName();
        } catch (Exception e) {
            throw new MyCustomException("Слово или перевод не нашлись");
        }
    }

    public Collection<TranslateModel> getDictionary(String dictionaryName) {
        return translateRepository.getDictionaryById(dictionaryService.getDictionary(dictionaryName).getId());
    }

    public UUID createTranslate(TranslateModel translateModel) {
        return translateRepository.createNewTranslate(translateModel.getWordModelFrom(),
                translateModel.getWordModelTo(),
                translateModel.getLanguageId());
    }

    public PageResultModel getPage(TranslatePageModel model) {
        return translateRepository.getPage(model);
    }
}
