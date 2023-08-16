package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import com.example.springliquidbase.domain.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import io.ebean.PagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {

    DbModel db;

    @Autowired
    public DictionaryRepository(DbModel db) {
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

    private DictionaryEntity getEntity(UUID languageFromId, UUID languageToId, String name) {
        var entity = new DictionaryEntity();
        entity.setId(UUID.randomUUID());
        entity.setLanguageFromId(languageFromId);
        entity.setLanguageToId(languageToId);
        entity.setName(name);
        return entity;
    }

    public DictionaryModel find(String name) {
        DictionaryEntity dictionary = db.getDb().find(DictionaryEntity.class).where().eq(DictionaryEntity.NAME, name).findOne();
        return getModel(dictionary);
    }

    public Collection<DictionaryModel> findAll() {
        List<DictionaryEntity> dictionaryEntities = db.getDb().find(DictionaryEntity.class).findList();
        return dictionaryEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public UUID createNewDictionary(UUID languageFrom, UUID languageTo, String name) {
        var entity = getEntity(languageFrom, languageTo, name);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public PageResultModel getPage(DictionaryPageModel model) {
        PagedList<DictionaryEntity> pagedList = db.getDb().find(DictionaryEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).findPagedList();
        List<DictionaryModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());


        return new PageResultModel(pagedList.getTotalCount(), models);
    }
}
