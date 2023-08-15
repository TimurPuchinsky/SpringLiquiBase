package com.example.springliquidbase.infrastructure.repository.translaterepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.DictionaryModel;
import com.example.springliquidbase.domain.TranslateModel;
import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryEntity;
import com.example.springliquidbase.infrastructure.repository.wordrepository.WordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TranslateRepository {

    ServerConfig db;

    @Autowired
    public TranslateRepository(ServerConfig db) {
        this.db = db;
    }

    private TranslateModel getModel(TranslateEntity e) {
        var model = new TranslateModel();
        model.setId(e.getId());
        model.setWordModelFrom(e.getWordFromId());
        model.setWordModelTo(e.getWordToId());
        model.setDictionary(e.getDictionaryId());
        return model;
    }

    public TranslateModel getTranslateByWord(String word, UUID dictionary) {
        WordEntity wordEntity = db.database().find(WordEntity.class).where().eq(WordEntity.NAME, word).findOne();
        TranslateEntity translateEntity = db.database().find(TranslateEntity.class).where().eq(TranslateEntity.WORDFROMID, wordEntity.getId())
                .and().eq(TranslateEntity.DICTIONARYID, dictionary).findOne();
        return getModel(translateEntity);
    }

    public void createNewTranslate(UUID wordModelFrom, UUID wordModelTo, UUID dictionary) {
        var entity = new TranslateEntity();
        entity.setId(UUID.randomUUID());
        entity.setWordFromId(wordModelFrom);
        entity.setWordToId(wordModelTo);
        entity.setDictionaryId(dictionary);
        db.database().insert(entity);
    }

    public Collection<TranslateModel> getDictionaryById(UUID dictionaryId) {
        List<TranslateEntity> translateEntity = db.database().find(TranslateEntity.class).where().eq(TranslateEntity.DICTIONARYID, dictionaryId).findList();
        return translateEntity.stream().map(this::getModel).collect(Collectors.toList());
    }
}
