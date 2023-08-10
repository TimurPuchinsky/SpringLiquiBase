package com.example.springliquidbase;

import com.example.springliquidbase.infrastructure.repository.languagerepository.Language;
import io.ebean.DB;
import org.junit.jupiter.api.Test;

public class LanguageTest {
    @Test
    public void selectLanguage() {

        DB.find(Language.class)
                .select("language")
                .findList();
    }
}