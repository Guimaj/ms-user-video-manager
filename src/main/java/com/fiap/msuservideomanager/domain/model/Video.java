package com.fiap.msuservideomanager.domain.model;

public class Video {
    private String codigo;
    private String nome;
    private String tipo;
    private String tamanho;
    private String status;

    public Video() {}

    public Video(String codigo, String nome, String tipo, String tamanho, String status) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
