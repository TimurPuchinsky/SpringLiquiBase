package com.example.springliquidbase.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExportModel {

    private String word_from;
    private String word_to;
    private String author_fio;
    private LocalDateTime created;
    private String changer_fio;
    private LocalDateTime changed;
    private UUID author_id;
    private UUID changer_id;
    private UUID dictionary_id;
    private String author_email;
}
