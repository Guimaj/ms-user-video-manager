package com.fiap.msuservideomanager.entrypoint.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ArquivoDTOTest {

    @Test
    void deveCriarArquivoDTO_Corretamente() {
        ArquivoDTO arquivoDTO = new ArquivoDTO("video.mp4", "mp4", "500", "10");

        assertThat(arquivoDTO.getNome()).isEqualTo("video.mp4");
        assertThat(arquivoDTO.getTipo()).isEqualTo("mp4");
        assertThat(arquivoDTO.getTamanho()).isEqualTo("500");
        assertThat(arquivoDTO.getIntervalo()).isEqualTo("10");
    }

    @Test
    void devePermitirModificarValores() {
        ArquivoDTO arquivoDTO = new ArquivoDTO();

        arquivoDTO.setNome("teste.mp4");
        arquivoDTO.setTipo("mp4");
        arquivoDTO.setTamanho("100");
        arquivoDTO.setIntervalo("10");

        assertThat(arquivoDTO.getNome()).isEqualTo("teste.mp4");
        assertThat(arquivoDTO.getTipo()).isEqualTo("mp4");
        assertThat(arquivoDTO.getTamanho()).isEqualTo("100");
        assertThat(arquivoDTO.getIntervalo()).isEqualTo("10");
    }

    @Test
    void deveCriarArquivoDTO_Vazio() {
        ArquivoDTO arquivoDTO = new ArquivoDTO();

        assertThat(arquivoDTO).isNotNull();
        assertThat(arquivoDTO.getNome()).isNull();
        assertThat(arquivoDTO.getTipo()).isNull();
        assertThat(arquivoDTO.getTamanho()).isNull();
        assertThat(arquivoDTO.getIntervalo()).isNull();
    }
}
