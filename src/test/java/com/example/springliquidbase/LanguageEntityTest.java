package com.example.springliquidbase;

import com.example.springliquidbase.infrastructure.repository.languagerepository.LanguageEntity;
import io.ebean.DB;
import org.junit.jupiter.api.Test;

public class LanguageEntityTest {
    @Test
    public void selectLanguage() {

        DB.find(LanguageEntity.class)
                .select("language")
                .findList();
    }
}