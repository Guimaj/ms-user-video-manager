package com.fiap.msuservideomanager.entrypoint.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenDTO {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
