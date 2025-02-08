package com.fiap.msuservideomanager.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoDTO {
    private String nome;
    private String tipo;
    private String tamanho;
    private String status;
}
