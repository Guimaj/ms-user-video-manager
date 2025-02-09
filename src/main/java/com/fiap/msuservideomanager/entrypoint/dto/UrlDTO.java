package com.fiap.msuservideomanager.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UrlDTO {
    private String url;
    @JsonProperty("x-amz-meta-arquivo-id")
    private String idArquivo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(String idArquivo) {
        this.idArquivo = idArquivo;
    }
}
