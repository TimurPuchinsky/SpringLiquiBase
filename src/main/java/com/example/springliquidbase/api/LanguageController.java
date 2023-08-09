package com.example.springliquidbase.api;

import com.example.springliquidbase.infrastructure.database.Database;
import com.example.springliquidbase.infrastructure.repository.languagerepository.Language;
import io.ebean.DB;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/language")
@AllArgsConstructor
public class LanguageController {

    private final Database db;

    @GetMapping("/showAll")
    public List<Language> showLanguages(){
        db.database();
        return DB.find(Language.class)
                .select("language")
                .findList();
    }
}
