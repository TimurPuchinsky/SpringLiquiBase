package com.example.springliquidbase.domain.translate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TranslateResultModel {

    private String wordStringFrom;
    private String wordStringTo;
    private UUID author_id;
    private LocalDateTime created;
    private UUID changer_id;
    private LocalDateTime changed;
    private UUID dictionary_id;
}
