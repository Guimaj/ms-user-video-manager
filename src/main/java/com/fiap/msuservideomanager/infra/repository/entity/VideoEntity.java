package com.fiap.msuservideomanager.infra.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "video")
public class VideoEntity {
    @Id
    private String codigo;
    private String nome;
    private String tipo;
    private String tamanho;
    private String intervalo;
    private String status;
    private String email;
    private String usuarioId;
}
