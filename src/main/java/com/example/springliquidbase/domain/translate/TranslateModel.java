package com.example.springliquidbase.domain.translate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateModel{

    private UUID id;
    private UUID wordFromId;
    private UUID wordToId;
    private UUID dictionary_id;
    private UUID author_id;
    private LocalDateTime created;
    private UUID changer_id;
    private LocalDateTime changed;
}
