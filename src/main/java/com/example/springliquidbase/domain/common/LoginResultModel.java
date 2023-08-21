package com.example.springliquidbase.domain.common;


import lombok.Getter;
import lombok.Setter;
import org.apache.el.parser.Token;

@Getter
@Setter
public class LoginResultModel extends GeneralResultModel{

    public LoginResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public LoginResultModel(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;
    private String refreshToken;
}
