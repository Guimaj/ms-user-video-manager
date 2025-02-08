package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.port.ArquivoPort;
import com.fiap.msuservideomanager.application.usecase.UrlVideoUseCase;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlVideoUseCaseTest {

    @Mock
    private ArquivoPort arquivoPort;

    @InjectMocks
    private UrlVideoUseCase urlVideoUseCase;

    private Arquivo arquivo;
    private Url url;

    @BeforeEach
    void setUp() {
        arquivo = new Arquivo("video.mp4", "mp4", "1024", "10");
        url = new Url("https://mocked-url.com/video.mp4", "123");
    }

    @Test
    void testGeraUrlVideo_DeveRetornarUrl() {
        when(arquivoPort.gerarUrlArquivo(any(Arquivo.class), eq("userId"))).thenReturn(url);

        Url resultado = urlVideoUseCase.geraUrlVideo(arquivo, "userId");

        assertNotNull(resultado);
        assertEquals(url.getUrl(), resultado.getUrl());
        assertEquals(url.getId(), resultado.getId());

        verify(arquivoPort, times(1)).gerarUrlArquivo(any(Arquivo.class), eq("userId"));
    }

    @Test
    void testGeraUrlVideo_QuandoErroAoGerarUrl_DeveLancarExcecao() {
        when(arquivoPort.gerarUrlArquivo(any(Arquivo.class), eq("userId")))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao gerar URL do vídeo"));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            urlVideoUseCase.geraUrlVideo(arquivo, "userId");
        });

        assertEquals(500, thrown.getStatusCode().value());
        assertEquals("Erro ao gerar URL do vídeo", thrown.getReason());

        verify(arquivoPort, times(1)).gerarUrlArquivo(any(Arquivo.class), eq("userId"));
    }
}
