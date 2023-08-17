package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.StringResultModel;
import com.example.springliquidbase.domain.translate.TranslateModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.domain.translate.TranslateResultModel;
import com.example.springliquidbase.domain.word.WordModel;
import com.example.springliquidbase.infrastructure.repository.translaterepository.TranslateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TranslateService {

    private final TranslateRepository translateRepository;
    private final DictionaryService dictionaryService;
    private final WordService wordService;

    public StringResultModel getTranslate(String word, String languageFrom, String languageTo) {
            var wordId = wordService.getWordByName(word);
            if (wordId == null) {
                return new StringResultModel("NullException", "Слово не нашлось");
            }
            var dictionaryId = dictionaryService.getDictionaryByLanguages(languageFrom, languageTo);
            if (dictionaryId == null) {
                return new StringResultModel("NullException", "Словаварь не нашелся");
            }
            var translate = translateRepository.getTranslateByWord(
                    wordId.getId(),
                    dictionaryId.getId());
            if (translate == null) {
                return new StringResultModel("NullException", "Перевод не нашелся");
            }
            return new StringResultModel(wordService.getWordById(translate).getName());
    }

    public Collection<TranslateModel> getDictionary(String dictionaryName) {
        return translateRepository.getDictionaryById(dictionaryService.getDictionary(dictionaryName).getId());
    }

    public GuidResultModel createTranslate(TranslateModel translateModel) {
        var wordFrom = translateModel.getWordFromId();
        if (wordFrom == null) {
            return new GuidResultModel("NullException", "Слово не нашлось");
        }
        var wordTo = translateModel.getWordToId();
        if (wordTo == null) {
            return new GuidResultModel("NullException", "Перевод не нашелся");
        }
        var dictionary = translateModel.getDictionaryId();
        if (dictionary == null) {
            return new GuidResultModel("NullException", "Словарь не нашелся");
        }
        var createTranslate = translateRepository.createNewTranslate(wordFrom, wordTo, dictionary);
        if (createTranslate == null) {
            return new GuidResultModel("Error", "Перевод не создан");
        }
        return new GuidResultModel(createTranslate);
    }

    public PageResultModel<TranslateResultModel> getPage(TranslatePageModel model) {
        var getDictionary = dictionaryService.getDictionaryByLanguages(model.getLanguageFrom(), model.getLanguageTo());
        if (getDictionary == null) {
            return new PageResultModel<>(0, new ArrayList<>());
        }

        var wordsPage = translateRepository.getPage(model,
                getDictionary.getId());

        var wordIds = new ArrayList<UUID>();
        for (var translate : wordsPage.getList()) {
            wordIds.add(translate.getWordFromId());
            wordIds.add(translate.getWordToId());
        }

        var wordMap = wordService.getWordByIds(wordIds).stream().collect(Collectors.toMap(WordModel::getId, Function.identity()));
        return new PageResultModel<>(wordsPage.getTotalCount(),
                wordsPage.getList().stream().map(translateModel -> {

                    var translateResult = new TranslateResultModel();

                    var wordFrom = wordMap.get(translateModel.getWordFromId());
                    if (wordFrom != null) {
                        translateResult.setWordStringFrom(wordFrom.getName());
                    }

                    var wordTo = wordMap.get(translateModel.getWordToId());
                    if (wordTo != null) {
                        translateResult.setWordStringTo(wordTo.getName());
                    }
                    return translateResult;
                }).collect(Collectors.toList()));
    }
}