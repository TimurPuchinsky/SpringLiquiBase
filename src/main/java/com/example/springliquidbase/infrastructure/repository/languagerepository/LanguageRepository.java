package com.example.springliquidbase.infrastructure.repository.languagerepository;

import com.example.springliquidbase.config.DataBase;
import com.example.springliquidbase.domain.LanguageModel;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class LanguageRepository {

    DataBase db = new DataBase();
    Database database = db.database();

    public List<LanguageModel> findAll() {
        List<Language> languages = database.find(Language.class)
                .select("name")
                .findList();

        LanguageModel languageModel = new LanguageModel();
        List<LanguageModel> languageModels = new LinkedList<>();

        for (Language s : languages){
            languageModel.setId(s.getId());
            languageModel.setLanguage(s.getName());
            languageModels.add(languageModel);
        }
        return languageModels;
    }

    public void createNewLanguage(String languageModel) {
        var entity = new Language();
        entity.setName(languageModel);
        entity.setId(UUID.randomUUID());
        database.insert(entity);
    }
}
