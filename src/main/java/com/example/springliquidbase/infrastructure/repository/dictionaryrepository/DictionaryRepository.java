package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.DictionaryModel;
import com.example.springliquidbase.domain.LanguageModel;
import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {

    ServerConfig db;

    @Autowired
    public DictionaryRepository(ServerConfig db) {
        this.db = db;
    }

    private DictionaryModel getModel(DictionaryEntity e) {
        var model = new DictionaryModel();
        model.setId(e.getId());
        model.setName(e.getName());
        model.setLanguageFrom(e.getLanguageFromId());
        model.setLanguageTo(e.getLanguageToId());
        return model;
    }

    public Collection<DictionaryModel> findAll() {
        List<DictionaryEntity> dictionaryEntities = db.database().find(DictionaryEntity.class).findList();
        return dictionaryEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public void createNewDictionary(UUID languageFrom, UUID languageTo, String name) {
        var entity = new DictionaryEntity();

        entity.setId(UUID.randomUUID());
        entity.setName(name);
        entity.setLanguageFromId(languageFrom);
        entity.setLanguageToId(languageTo);
        db.database().insert(entity);
    }
}
