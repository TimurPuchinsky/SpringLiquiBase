package com.example.springliquidbase.infrastructure.repository.translaterepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.dictionary.DictionaryModel;
import com.example.springliquidbase.domain.dictionary.DictionaryPageModel;
import com.example.springliquidbase.domain.translate.TranslateModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import com.example.springliquidbase.infrastructure.repository.dictionaryrepository.DictionaryEntity;
import io.ebean.ExpressionList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TranslateRepository {

    DbModel db;

    @Autowired
    public TranslateRepository(DbModel db) {
        this.db = db;
    }

    private TranslateModel getModel(TranslateEntity e) {
        if (e == null) return null;
        var model = new TranslateModel();
        model.setId(e.getId());
        model.setWordFromId(e.getWordFromId());
        model.setWordToId(e.getWordToId());
        model.setDictionaryId(e.getDictionaryId());
        return model;
    }

    private TranslateEntity getEntity(UUID wordFromId, UUID wordToId, UUID dictionaryId) {
        var entity = new TranslateEntity();
        entity.setId(UUID.randomUUID());
        entity.setWordFromId(wordFromId);
        entity.setWordToId(wordToId);
        entity.setDictionaryId(dictionaryId);
        return entity;
    }

    public UUID getTranslateByWord(UUID word, UUID dictionary) {
        var translateEntity = db.getDb().find(TranslateEntity.class).where().eq(TranslateEntity.WORD_FROM_ID, word)
                .and().eq(TranslateEntity.DICTIONARY_ID, dictionary).findOne();
        return getModel(translateEntity).getWordToId();
    }

    public UUID createNewTranslate(UUID wordModelFrom, UUID wordModelTo, UUID language) {
        var entity = getEntity(wordModelFrom, wordModelTo, language);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public Collection<TranslateModel> getDictionaryById(UUID dictionaryId) {
        var translateEntity = db.getDb().find(TranslateEntity.class).where().eq(TranslateEntity.DICTIONARY_ID, dictionaryId).findList();
        return translateEntity.stream().map(this::getModel).collect(Collectors.toList());
    }

//    public PageResultModel<TranslateModel> getPage1(TranslatePageModel model, UUID dictionary) {
//        var pagedList = db.getDb().find(TranslateEntity.class)
//                .where().eq(TranslateEntity.DICTIONARY_ID, dictionary)
//                .setMaxRows(model.getPageSize())
//                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).findPagedList();
//
//        return new PageResultModel<>(
//                pagedList.getTotalCount(),
//                pagedList.getList().stream().map(this::getModel).collect(Collectors.toList()));
//    }

    public PageResultModel<TranslateModel> getPage(TranslatePageModel model, UUID dictionary) {
        var exp = db.getDb().find(TranslateEntity.class)
                .where().eq(TranslateEntity.DICTIONARY_ID, dictionary)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).where();

        //exp = applyFilters(exp);

        var pagedList = exp.findPagedList();
        List<TranslateModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());
        return new PageResultModel<>(pagedList.getTotalCount(), models);
    }

//    private ExpressionList<TranslateEntity> applyFilters(ExpressionList<TranslateEntity> exp) {
//
//        return exp;
//    }
}
