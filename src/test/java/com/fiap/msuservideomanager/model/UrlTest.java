package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Url;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UrlTest {

    @Test
    void deveCriarUrl_Corretamente() {
        Url url = new Url("https://meuarquivo.com/video.mp4", "12345");

        assertThat(url.getUrl()).isEqualTo("https://meuarquivo.com/video.mp4");
        assertThat(url.getId()).isEqualTo("12345");
    }

    @Test
    void devePermitirModificarValores() {
        Url url = new Url();

        url.setUrl("https://meuarquivo.com/novo-video.mp4");
        url.setId("67890");

        assertThat(url.getUrl()).isEqualTo("https://meuarquivo.com/novo-video.mp4");
        assertThat(url.getId()).isEqualTo("67890");
    }

    @Test
    void deveCriarUrl_Vazia() {
        Url url = new Url();

        assertThat(url).isNotNull();
        assertThat(url.getUrl()).isNull();
        assertThat(url.getId()).isNull();
    }
}
