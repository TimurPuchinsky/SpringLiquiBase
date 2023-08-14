package com.example.springliquidbase.infrastructure.repository.languagerepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.LanguageModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {

    ServerConfig db;

    @Autowired
    public LanguageRepository(ServerConfig db) {
        this.db = db;
    }

    public Collection<LanguageModel> findAll() {
        List<LanguageEntity> languageEntities = db.database().find(LanguageEntity.class)
                .findList();

        return languageEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    private LanguageModel getModel(LanguageEntity e) {
        var model = new LanguageModel();
        model.setName(e.getName());
        model.setId(e.getId());
        return model;
    }

    public void createNewLanguage(String languageModel) {
        var entity = new LanguageEntity();
        entity.setName(languageModel);
        entity.setId(UUID.randomUUID());
        db.database().insert(entity);
    }

    public void removeLanguageByName(String name) {
        db.database().find(LanguageEntity.class).where().eq(LanguageEntity.NAME, name).delete();
    }
}
