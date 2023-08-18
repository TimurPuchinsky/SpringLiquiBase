package com.example.springliquidbase.infrastructure.repository.wordrepository;

import com.example.springliquidbase.domain.common.PageResultModel;
import com.example.springliquidbase.domain.word.WordModel;
import com.example.springliquidbase.domain.word.WordPageModel;
import com.example.springliquidbase.infrastructure.repository.DbModel;
import io.ebean.ExpressionList;
import io.ebean.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class WordRepository {

    DbModel db;

    public WordRepository(DbModel db) {
        this.db = db;
    }

    private WordModel getModel(WordEntity e) {
        if (e == null) return null;
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

//    public Collection<WordModel> findAll() {
//        List<WordEntity> wordEntities = db.getDb().find(WordEntity.class).findList();
//        return wordEntities.stream().map(this::getModel).collect(Collectors.toList());
//    }

    public PageResultModel getPage(WordPageModel model) {
        var exp = db.getDb().find(WordEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow(model.getPageNum() * model.getPageSize() - 1).where();

        exp = applyFilters(exp, model);

        var pagedList = exp.findPagedList();
        List<WordModel> models = pagedList.getList().stream().map(this::getModel).collect(Collectors.toList());
        return new PageResultModel(pagedList.getTotalCount(), models);
    }

    private ExpressionList<WordEntity> applyFilters(ExpressionList<WordEntity> exp, WordPageModel model) {
        if (StringUtils.isNotBlank(model.getNameFilter())) {
            exp = exp.ilike(WordEntity.NAME, "%" + model.getNameFilter() + "%");
        }
        return exp;
    }

    public UUID createNewWord(String word, UUID language) {
        var entity = getEntity(word, language);
        db.getDb().insert(entity);
        return entity.getId();
    }

    public boolean removeWordByName(String name) {
        var res = db.getDb().find(WordEntity.class).where().eq(WordEntity.NAME, name).delete();
        return res == 1;
    }

    public WordModel findWordByName(String name) {
        WordEntity wordEntity = db.getDb().find(WordEntity.class).where().eq(WordEntity.NAME, name).findOne();
        return getModel(wordEntity);
    }

    public WordModel findWordById(UUID id) {
        WordEntity wordEntity = db.getDb().find(WordEntity.class).where().eq(WordEntity.ID, id).findOne();
        return getModel(wordEntity);
    }

    public Collection<WordModel> findWordsByIds(Collection<UUID> ids) {
        Query<WordEntity> query = db.getDb().find(WordEntity.class).where().in(WordEntity.ID, ids).query();

        return query.findList()
                .stream()
                .map(this::getModel)
                .collect(Collectors.toList());
    }
}
