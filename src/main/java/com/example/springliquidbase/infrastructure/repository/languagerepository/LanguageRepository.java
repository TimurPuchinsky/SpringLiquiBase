package com.example.springliquidbase.infrastructure.repository.languagerepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.LanguageModel;

import com.example.springliquidbase.infrastructure.repository.DbModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {

    DbModel db;

    public LanguageRepository(DbModel db) {
        this.db = db;
    }

    private LanguageModel getModel(LanguageEntity e) {
        var model = new LanguageModel();
        model.setName(e.getName());
        model.setId(e.getId());
        return model;
    }

    private LanguageEntity getEntity(String m) {
        var entity = new LanguageEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(m);
        return entity;
    }

    public Collection<LanguageModel> findAll() {
        List<LanguageEntity> languageEntities = db.getDb().find(LanguageEntity.class)
                .findList();
        return languageEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public LanguageModel find(String name) {
        LanguageEntity languageEntity = db.getDb().find(LanguageEntity.class).where().eq(LanguageEntity.NAME, name).findOne();
        return getModel(languageEntity);
    }

    public UUID createNewLanguage(String languageModel) {
        var entity = getEntity(languageModel);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public int removeLanguageByName(String name) {
        return db.getDb().find(LanguageEntity.class).where().eq(LanguageEntity.NAME, name).delete();
    }
}
