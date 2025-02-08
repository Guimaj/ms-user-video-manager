package com.fiap.msuservideomanager.domain.model;

public class Arquivo {
    private String nome;
    private String tipo;
    private String tamanho;
    private String intervalo;

    public Arquivo() {}

    public Arquivo(String nome, String tipo, String tamanho, String intervalo) {
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.intervalo = intervalo;
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

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }
}
