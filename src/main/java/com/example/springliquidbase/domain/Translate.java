package com.example.springliquidbase.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translate {

    private Integer id;
    private Word wordFrom;
    private Word wordTo;
    private Dictionary dictionary;
}
