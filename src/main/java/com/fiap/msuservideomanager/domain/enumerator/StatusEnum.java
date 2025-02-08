package com.fiap.msuservideomanager.domain.enumerator;

public enum StatusEnum {
    PENDENTE("Pendente upload do arquivo"),
    CONCLUIDO("Processamento concluido"),
    PROCESSANDO("Arquivo em processamento");

    private final String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
