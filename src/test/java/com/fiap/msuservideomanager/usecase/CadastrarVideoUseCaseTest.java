package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.port.VideoPort;
import com.fiap.msuservideomanager.application.usecase.CadastrarVideoUseCase;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CadastrarVideoUseCaseTest {

    @Mock
    private VideoPort videoPort;

    @InjectMocks
    private CadastrarVideoUseCase cadastrarVideoUseCase;

    private Arquivo arquivo;
    private Video video;

    @BeforeEach
    void setUp() {
        arquivo = new Arquivo("video.mp4", "mp4", "1024", "10");
        video = new Video("123", "video.mp4", "mp4", "1024", "PENDENTE");
    }

    @Test
    void testCadastraVideo_DeveRetornarVideo() {
        when(videoPort.cadastrarVideo(any(Arquivo.class), eq("userId"), eq("123"), eq("user@example.com")))
                .thenReturn(video);

        Video resultado = cadastrarVideoUseCase.cadastraVideo(arquivo, "userId", "123", "user@example.com");

        assertNotNull(resultado);
        assertEquals(video.getCodigo(), resultado.getCodigo());
        assertEquals(video.getNome(), resultado.getNome());
        assertEquals(video.getTipo(), resultado.getTipo());
        assertEquals(video.getTamanho(), resultado.getTamanho());
        assertEquals(video.getStatus(), resultado.getStatus());

        verify(videoPort, times(1)).cadastrarVideo(any(Arquivo.class), eq("userId"), eq("123"), eq("user@example.com"));
    }

    @Test
    void testCadastraVideo_QuandoErroAoCadastrar_DeveLancarExcecao() {
        when(videoPort.cadastrarVideo(any(Arquivo.class), eq("userId"), eq("123"), eq("user@example.com")))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao cadastrar vídeo"));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            cadastrarVideoUseCase.cadastraVideo(arquivo, "userId", "123", "user@example.com");
        });

        assertEquals(500, thrown.getStatusCode().value());
        assertEquals("Erro ao cadastrar vídeo", thrown.getReason());

        verify(videoPort, times(1)).cadastrarVideo(any(Arquivo.class), eq("userId"), eq("123"), eq("user@example.com"));
    }
}
