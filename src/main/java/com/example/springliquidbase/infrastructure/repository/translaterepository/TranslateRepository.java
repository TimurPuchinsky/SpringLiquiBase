package com.example.springliquidbase.infrastructure.repository.translaterepository;

import com.example.springliquidbase.domain.PageResultModel;
import com.example.springliquidbase.domain.translate.TranslateModel;
import com.example.springliquidbase.domain.translate.TranslatePageModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import io.ebean.PagedList;
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
        var model = new TranslateModel();
        model.setId(e.getId());
        model.setWordModelFrom(e.getWordFromId());
        model.setWordModelTo(e.getWordToId());
        model.setDictionaryId(e.getDictionaryId());
        return model;
    }

    private TranslateEntity getEntity(UUID wordFromId, UUID wordToId, UUID languageId) {
        var entity = new TranslateEntity();
        entity.setId(UUID.randomUUID());
        entity.setWordFromId(wordFromId);
        entity.setWordToId(wordToId);
        entity.setDictionaryId(languageId);
        return entity;
    }

    public UUID getTranslateByWord(UUID word, UUID dictionary) {
        TranslateEntity translateEntity = db.getDb().find(TranslateEntity.class).where().eq(TranslateEntity.WORD_FROM_ID, word)
                .and().eq(TranslateEntity.DICTIONARY_ID, dictionary).findOne();
        return getModel(translateEntity).getWordModelTo();
    }

    public UUID createNewTranslate(UUID wordModelFrom, UUID wordModelTo, UUID language) {
        var entity = getEntity(wordModelFrom, wordModelTo, language);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public Collection<TranslateModel> getDictionaryById(UUID dictionaryId) {
        List<TranslateEntity> translateEntity = db.getDb().find(TranslateEntity.class).where().eq(TranslateEntity.DICTIONARY_ID, dictionaryId).findList();
        return translateEntity.stream().map(this::getModel).collect(Collectors.toList());
    }

    public PageResultModel getPage(TranslatePageModel model, UUID dictionary) {
        PagedList<TranslateEntity> pagedList = db.getDb().find(TranslateEntity.class)
                .where().eq(TranslateEntity.DICTIONARY_ID, dictionary)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).findPagedList();
        List<TranslateModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());

        return new PageResultModel(pagedList.getTotalCount(), models);
    }
}
