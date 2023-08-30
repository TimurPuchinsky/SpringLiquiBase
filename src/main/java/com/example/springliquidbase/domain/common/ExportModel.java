package com.example.springliquidbase.domain.common;

import com.example.springliquidbase.domain.user.UserFullnameModel;
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
    private UserFullnameModel author_fio;
    private LocalDateTime created;
    private UserFullnameModel changer_fio;
    private LocalDateTime changed;
    private UUID author_id;
    private UUID changer_id;
}
