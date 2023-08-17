package com.example.springliquidbase.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GuidResultModel extends GeneralResultModel{

    public GuidResultModel(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }

    public GuidResultModel(UUID result) {
        this.result = result;
    }

    private UUID result;
}
