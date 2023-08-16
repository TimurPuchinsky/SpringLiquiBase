package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class WordRepository {

    DbModel db;

    @Autowired
    public WordRepository(DbModel db) {
        this.db = db;
    }

    private WordModel getModel(WordEntity e) {
        var model = new WordModel();
        model.setId(e.getId());
        model.setName(e.getName());
        model.setLanguageId(e.getLanguageId());
        return model;
    }

    private WordEntity getEntity(String name, UUID languageId) {
        var entity = new WordEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(name);
        entity.setLanguageId(languageId);
        return entity;
    }

    public Collection<WordModel> findAll() {
        List<WordEntity> wordEntities = db.getDb().find(WordEntity.class).findList();
        return wordEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public UUID createNewWord(String word, UUID language) {
        var entity = getEntity(word, language);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public int removeWordByName(String name) {
        return db.getDb().find(WordEntity.class).where().eq(WordEntity.NAME, name).delete();
    }

    public WordModel findWordByName(String name) {
        WordEntity wordEntity = db.getDb().find(WordEntity.class).where().eq(WordEntity.NAME, name).findOne();
        return getModel(wordEntity);
    }

    public WordModel findWordById(UUID id) {
        WordEntity wordEntity = db.getDb().find(WordEntity.class).where().eq(WordEntity.ID, id).findOne();
        return getModel(wordEntity);
    }
}
