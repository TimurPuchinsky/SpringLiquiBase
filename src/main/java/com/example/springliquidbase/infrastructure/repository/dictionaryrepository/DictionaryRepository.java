package com.example.springliquidbase.infrastructure.repository.dictionaryrepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.domain.word.WordModel;
import com.example.springliquidbase.domain.word.WordPageModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import com.example.springliquidbase.infrastructure.repository.userrepository.UserEntity;
import com.example.springliquidbase.infrastructure.repository.wordrepository.WordEntity;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {

    DbModel db;

    @Autowired
    public DictionaryRepository(DbModel db) {
        this.db = db;
    }

    private DictionaryModel getModel(DictionaryEntity e) {
        if (e == null) return null;
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

//    public PageResultModel getPage(DictionaryPageModel model) {
//        PagedList<DictionaryEntity> pagedList = db.getDb().find(DictionaryEntity.class)
//                .setMaxRows(model.getPageSize())
//                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).findPagedList();
//        List<DictionaryModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());
//        return new PageResultModel(pagedList.getTotalCount(), models);
//    }

    public PageResultModel getPage(DictionaryPageModel model) {
        var exp = db.getDb().find(DictionaryEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).where();

        exp = applyFilters(exp, model);

        var pagedList = exp.findPagedList();
        List<DictionaryModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());
        return new PageResultModel<>(pagedList.getTotalCount(), models);
    }

    private ExpressionList<DictionaryEntity> applyFilters(ExpressionList<DictionaryEntity> exp, DictionaryPageModel model) {
        if (StringUtils.isNotBlank(model.getNameFilter())) {
            exp = exp.ilike(DictionaryEntity.NAME, "%" + model.getNameFilter() + "%");
        }
        return exp;
    }

    public DictionaryModel getDictionaryLanguages(UUID languageFrom, UUID languageTo) {
        DictionaryEntity dictionary = db.getDb().find(DictionaryEntity.class)
                .where().eq(DictionaryEntity.LANGUAGE_FROM_ID, languageFrom)
                .and().eq(DictionaryEntity.LANGUAGE_TO_ID, languageTo).findOne();
        return getModel(dictionary);
    }

    public List<DictionaryModel> getAllDictionaries() {
        var dictionaries = db.getDb().find(DictionaryEntity.class).findList();
        return dictionaries.stream().map(this::getModel).collect(Collectors.toList());
    }

    public DictionaryModel getDictionaryById(UUID dictionaryId) {
        var dict = db.getDb().find(DictionaryEntity.class).where().eq(DictionaryEntity.ID, dictionaryId).findOne();
        return getModel(dict);
    }

    public Map<UUID, DictionaryModel> getDictionariesListById(List<UUID> dictionariesIds) {
        Map<UUID, DictionaryModel> dictionaryModelMap = new HashMap<>();
        for (DictionaryEntity dictionary :
                db.getDb().find(DictionaryEntity.class).where().in(DictionaryEntity.ID, dictionariesIds).findList()) {
            DictionaryModel model = getModel(dictionary);
            dictionaryModelMap.put(model.getId(), model);
        }
        return dictionaryModelMap;
    }
}