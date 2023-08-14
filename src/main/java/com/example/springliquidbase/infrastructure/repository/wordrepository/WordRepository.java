package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.domain.WordModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class WordRepository {

    ServerConfig db;

    @Autowired
    public WordRepository(ServerConfig db) {
        this.db = db;
    }

    private WordModel getModel(WordEntity e) {
        var model = new WordModel();
        model.setId(e.getId());
        model.setName(e.getName());

        var languageModel = new LanguageModel();
        languageModel.setId(e.getLanguageId().getId());
        languageModel.setName(e.getLanguageId().getName());

        model.setLanguage_Entity_id(languageModel.getId());

        return model;
    }

    public Collection<WordModel> findAll() {
        List<WordEntity> wordEntities = db.database().find(WordEntity.class).findList();
        return wordEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public void createNewWord(String wordModel, UUID languageEntityId) {
        var entity = new WordEntity();
        var languageEntity = db.database().find(LanguageEntity.class)
                .where().eq(LanguageEntity.ID, languageEntityId).findOne();

        entity.setId(UUID.randomUUID());
        entity.setName(wordModel);
        entity.setLanguageId(languageEntity);
        db.database().insert(entity);
    }

    public void removeWordByName(String name) {
        db.database().find(WordEntity.class).where().eq(WordEntity.NAME, name).delete();
    }
}
