package com.fiap.msuservideomanager.entrypoint.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UrlDTOTest {

    @Test
    void deveCriarUrlDTO_Corretamente() {
        UrlDTO urlDTO = new UrlDTO("https://meuarquivo.com/video.mp4", "12345");

        assertThat(urlDTO.getUrl()).isEqualTo("https://meuarquivo.com/video.mp4");
        assertThat(urlDTO.getIdArquivo()).isEqualTo("12345");
    }

    @Test
    void devePermitirModificarValores() {
        UrlDTO urlDTO = new UrlDTO();

        urlDTO.setUrl("https://meuarquivo.com/novo-video.mp4");
        urlDTO.setIdArquivo("67890");

        assertThat(urlDTO.getUrl()).isEqualTo("https://meuarquivo.com/novo-video.mp4");
        assertThat(urlDTO.getIdArquivo()).isEqualTo("67890");
    }

    @Test
    void deveCriarUrlDTO_Vazio() {
        UrlDTO urlDTO = new UrlDTO();

        assertThat(urlDTO).isNotNull();
        assertThat(urlDTO.getUrl()).isNull();
        assertThat(urlDTO.getIdArquivo()).isNull();
    }
}
