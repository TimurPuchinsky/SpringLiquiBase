package com.example.springliquidbase.infrastructure.repository.languagerepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.language.LanguageModel;

import com.example.springliquidbase.domain.language.LanguagePageModel;
import com.example.springliquidbase.domain.user.UserModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import com.example.springliquidbase.infrastructure.repository.userrepository.UserEntity;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {

    DbModel db;

    public LanguageRepository(DbModel db) {
        this.db = db;
    }

    private LanguageModel getModel(LanguageEntity e) {
        if (e == null) return null;
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
        var languageEntities = db.getDb().find(LanguageEntity.class)
                .findList();
        return languageEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public LanguageModel getLanguageByName(String name) {
        var languageEntity = db.getDb().find(LanguageEntity.class).where().eq(LanguageEntity.NAME, StringUtils.capitalize(name)).findOne();
        return getModel(languageEntity);
    }

    public UUID createNewLanguage(String languageModel) {
        var entity = getEntity(languageModel);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public boolean removeLanguageByName(String name) {
         int res = db.getDb().find(LanguageEntity.class).where().eq(LanguageEntity.NAME, name).delete();
         return res == 1;
    }

    public PageResultModel getPage(LanguagePageModel model) {
        var exp = db.getDb().find(LanguageEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).where();

        exp = applyFilters(exp, model);

        var pagedList = exp.findPagedList();
        List<LanguageModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());
        return new PageResultModel<>(pagedList.getTotalCount(), models);
    }

    private ExpressionList<LanguageEntity> applyFilters(ExpressionList<LanguageEntity> exp, LanguagePageModel model) {
        if (StringUtils.isNotBlank(model.getNameFilter())) {
            exp = exp.ilike(LanguageEntity.NAME, "%" + model.getNameFilter() + "%");
        }
        return exp;
    }

    public Map<UUID, List<UUID>> getLanguagesIdsListByDict(Map<UUID, DictionaryModel> dictionaries) {
        Map<UUID, List<UUID>> languagePairs = new HashMap<>();
        for (DictionaryModel dictionary : dictionaries.values()) {
            List<UUID> languages = new ArrayList<>();
            languages.add(dictionary.getLanguageFrom());
            languages.add(dictionary.getLanguageTo());
            languagePairs.put(dictionary.getId(), languages);
        }
        return languagePairs;
    }

    public Map<UUID, List<LanguageModel>> getLanguagesListByDictionaryIds(Map<UUID, List<UUID>> languages) {
        Map<UUID, List<LanguageModel>> languagesByDictionaryId =
                languages.keySet().stream().collect(Collectors.toMap(
                        dictionaryId -> dictionaryId,
                        dictionaryId -> languages.get(dictionaryId).stream()
                                .map(languageId -> db.getDb().find(LanguageEntity.class, languageId))
                                .map(this::getModel)
                                .collect(Collectors.toList())
                ));
        return languagesByDictionaryId;
    }
}