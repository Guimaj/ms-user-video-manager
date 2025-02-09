package com.fiap.msuservideomanager.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDTO {
    private String url;
    @JsonProperty("x-amz-meta-arquivo-id")
    private String idArquivo;
}
