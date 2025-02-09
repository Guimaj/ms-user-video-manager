package com.fiap.msuservideomanager.domain.model;

public class Token {
    private String jwt;

    public Token() {}

    public Token(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
