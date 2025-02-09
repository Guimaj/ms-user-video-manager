package com.fiap.msuservideomanager.entrypoint.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VideoDTOTest {

    @Test
    void deveCriarVideoDTO_Corretamente() {
        VideoDTO videoDTO = new VideoDTO("video.mp4", "mp4", "500MB", "PROCESSANDO");

        assertThat(videoDTO.getNome()).isEqualTo("video.mp4");
        assertThat(videoDTO.getTipo()).isEqualTo("mp4");
        assertThat(videoDTO.getTamanho()).isEqualTo("500MB");
        assertThat(videoDTO.getStatus()).isEqualTo("PROCESSANDO");
    }

    @Test
    void devePermitirModificarValores() {
        VideoDTO videoDTO = new VideoDTO();

        videoDTO.setNome("novo_video.mp4");
        videoDTO.setTipo("avi");
        videoDTO.setTamanho("1GB");
        videoDTO.setStatus("FINALIZADO");

        assertThat(videoDTO.getNome()).isEqualTo("novo_video.mp4");
        assertThat(videoDTO.getTipo()).isEqualTo("avi");
        assertThat(videoDTO.getTamanho()).isEqualTo("1GB");
        assertThat(videoDTO.getStatus()).isEqualTo("FINALIZADO");
    }

    @Test
    void deveCriarVideoDTO_Vazio() {
        VideoDTO videoDTO = new VideoDTO();

        assertThat(videoDTO).isNotNull();
        assertThat(videoDTO.getNome()).isNull();
        assertThat(videoDTO.getTipo()).isNull();
        assertThat(videoDTO.getTamanho()).isNull();
        assertThat(videoDTO.getStatus()).isNull();
    }
}
