package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Video;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VideoTest {

    @Test
    void deveCriarVideo_Corretamente() {
        Video video = new Video("001", "video.mp4", "mp4", "500MB", "PROCESSANDO");

        assertThat(video.getCodigo()).isEqualTo("001");
        assertThat(video.getNome()).isEqualTo("video.mp4");
        assertThat(video.getTipo()).isEqualTo("mp4");
        assertThat(video.getTamanho()).isEqualTo("500MB");
        assertThat(video.getStatus()).isEqualTo("PROCESSANDO");
    }

    @Test
    void devePermitirModificarValores() {
        Video video = new Video();

        video.setCodigo("002");
        video.setNome("novo_video.avi");
        video.setTipo("avi");
        video.setTamanho("1GB");
        video.setStatus("FINALIZADO");

        assertThat(video.getCodigo()).isEqualTo("002");
        assertThat(video.getNome()).isEqualTo("novo_video.avi");
        assertThat(video.getTipo()).isEqualTo("avi");
        assertThat(video.getTamanho()).isEqualTo("1GB");
        assertThat(video.getStatus()).isEqualTo("FINALIZADO");
    }

    @Test
    void deveCriarVideo_Vazio() {
        Video video = new Video();

        assertThat(video).isNotNull();
        assertThat(video.getCodigo()).isNull();
        assertThat(video.getNome()).isNull();
        assertThat(video.getTipo()).isNull();
        assertThat(video.getTamanho()).isNull();
        assertThat(video.getStatus()).isNull();
    }
}
