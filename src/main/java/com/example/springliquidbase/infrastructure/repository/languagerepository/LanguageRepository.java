package com.example.springliquidbase.infrastructure.repository.languagerepository;

import com.example.springliquidbase.ServerConfig;
import com.example.springliquidbase.domain.LanguageModel;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LanguageRepository {

    ServerConfig db = new ServerConfig();

    public Collection<LanguageModel> findAll() {
        List<LanguageEntity> languageEntities = db.database().find(LanguageEntity.class)
                .findList();

        return languageEntities.stream().map(this::getModel).collect(Collectors.toList());
//
//        LanguageModel languageModel = new LanguageModel();
//        List<List<LanguageEntity>> languageModels = new LinkedList<>();
//        languageModels.add(languageEntities);
//
////        for (Language s : languages){
////            languageModel.setId(s.getId());
////            languageModel.setLanguage(s.getName());
////            languageModels.add(languageModel);
////        }
//        return languageModels;
    }

    private LanguageModel getModel(LanguageEntity e) {
        var model = new LanguageModel();
        model.setLanguage(e.getName());
        model.setId(e.getId());
        return model;
    }

    public void createNewLanguage(String languageModel) {
        var entity = new LanguageEntity();
        entity.setName(languageModel);
        entity.setId(UUID.randomUUID());
        db.database().insert(entity);
    }
}
