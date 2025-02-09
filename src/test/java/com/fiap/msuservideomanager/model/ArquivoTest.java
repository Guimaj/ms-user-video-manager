package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Arquivo;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ArquivoTest {

    @Test
    void deveCriarArquivo_Corretamente() {
        Arquivo arquivo = new Arquivo("video.mp4", "mp4", "500MB", "00:00 - 05:00");

        assertThat(arquivo.getNome()).isEqualTo("video.mp4");
        assertThat(arquivo.getTipo()).isEqualTo("mp4");
        assertThat(arquivo.getTamanho()).isEqualTo("500MB");
        assertThat(arquivo.getIntervalo()).isEqualTo("00:00 - 05:00");
    }

    @Test
    void devePermitirModificarValores() {
        Arquivo arquivo = new Arquivo();

        arquivo.setNome("audio.mp3");
        arquivo.setTipo("mp3");
        arquivo.setTamanho("3MB");
        arquivo.setIntervalo("00:10 - 00:30");

        assertThat(arquivo.getNome()).isEqualTo("audio.mp3");
        assertThat(arquivo.getTipo()).isEqualTo("mp3");
        assertThat(arquivo.getTamanho()).isEqualTo("3MB");
        assertThat(arquivo.getIntervalo()).isEqualTo("00:10 - 00:30");
    }

    @Test
    void deveCriarArquivo_Vazio() {
        Arquivo arquivo = new Arquivo();

        assertThat(arquivo).isNotNull();
        assertThat(arquivo.getNome()).isNull();
        assertThat(arquivo.getTipo()).isNull();
        assertThat(arquivo.getTamanho()).isNull();
        assertThat(arquivo.getIntervalo()).isNull();
    }
}
