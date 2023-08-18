package com.example.springliquidbase.domainservice;

import com.example.springliquidbase.domain.common.GuidResultModel;
import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.common.StringResultModel;
import com.example.springliquidbase.domain.common.SuccessResultModel;
import com.example.springliquidbase.domain.word.WordModel;
import com.example.springliquidbase.domain.word.WordPageModel;
import com.example.springliquidbase.infrastructure.repository.wordrepository.WordRepository;
import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final LanguageService languageService;

    public PageResultModel getPage(WordPageModel model) {
        return wordRepository.getPage(model);
    }

    public SuccessResultModel removeWord(String name) {
        var findWord = wordRepository.findWordByName(name);
        if (findWord == null) {
            return new SuccessResultModel("NullException", "слово не найдено");
        }
        var removeWord = wordRepository.removeWordByName(findWord.getName());
        return new SuccessResultModel(removeWord);
    }

    public GuidResultModel createWord(String word, String language) {
        var getLanguage = languageService.getLanguageByName(language);
        if (getLanguage == null) {
            return new GuidResultModel("NullException", "язык не нашелся");
        }
        return new GuidResultModel(wordRepository.createNewWord(StringUtils.capitalize(word), getLanguage.getId()));
    }

    public WordModel getWordByName(String name) {
        var word = wordRepository.findWordByName(StringUtils.capitalize(name));
        return word;
    }

    public WordModel getWordById(UUID uuid) {
        var word = wordRepository.findWordById(uuid);
        if (word == null) throw new NullPointerException();
        return word;
    }

    public Collection<WordModel> getWordByIds(Collection<UUID> ids) {
        var words = wordRepository.findWordsByIds(ids);
        if (words == null) throw new NullPointerException();
        return words;
    }
}