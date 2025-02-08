package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.port.VideoPort;
import com.fiap.msuservideomanager.application.usecase.ConsultarVideoUseCase;
import com.fiap.msuservideomanager.domain.enumerator.StatusEnum;
import com.fiap.msuservideomanager.domain.exception.StatusInvalidoException;
import com.fiap.msuservideomanager.domain.exception.VideoNaoEncontradoException;
import com.fiap.msuservideomanager.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ConsultarVideoUseCaseTest {

    @Mock
    private VideoPort videoPort;

    @InjectMocks
    private ConsultarVideoUseCase consultarVideoUseCase;

    private Video videoConcluido;
    private Video videoPendente;

    @BeforeEach
    void setUp() {
        videoConcluido = new Video("123", "video.mp4", "mp4", "1024", StatusEnum.CONCLUIDO.getDescricao());
        videoPendente = new Video("456", "video2.mp4", "mp4", "2048", StatusEnum.PENDENTE.getDescricao());
    }

    @Test
    void testConsultarVideosPorUsuario_DeveRetornarListaDeVideos() {
        when(videoPort.consultarVideosPorUsuario("userId")).thenReturn(List.of(videoConcluido, videoPendente));

        List<Video> resultado = consultarVideoUseCase.consultarVideosPorUsuario("userId");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(videoConcluido.getCodigo(), resultado.get(0).getCodigo());

        verify(videoPort, times(1)).consultarVideosPorUsuario("userId");
    }

    @Test
    void testConsultarVideoPorId_DeveRetornarVideo_SeStatusConcluido() {
        when(videoPort.consultarVideoPorId("123")).thenReturn(videoConcluido);

        Video resultado = consultarVideoUseCase.consultarVideoPorId("123");

        assertNotNull(resultado);
        assertEquals(videoConcluido.getCodigo(), resultado.getCodigo());
        assertEquals(StatusEnum.CONCLUIDO.getDescricao(), resultado.getStatus());

        verify(videoPort, times(1)).consultarVideoPorId("123");
    }

    @Test
    void testConsultarVideoPorId_QuandoVideoNaoExiste_DeveLancarExcecao() {
        when(videoPort.consultarVideoPorId("999")).thenReturn(null);

        VideoNaoEncontradoException thrown = assertThrows(VideoNaoEncontradoException.class, () -> {
            consultarVideoUseCase.consultarVideoPorId("999");
        });

        assertEquals("Video 999 não encontrado!", thrown.getReason());

        verify(videoPort, times(1)).consultarVideoPorId("999");
    }

    @Test
    void testConsultarVideoPorId_QuandoStatusNaoConcluido_DeveLancarExcecao() {
        when(videoPort.consultarVideoPorId("456")).thenReturn(videoPendente);

        StatusInvalidoException thrown = assertThrows(StatusInvalidoException.class, () -> {
            consultarVideoUseCase.consultarVideoPorId("456");
        });

        assertEquals("Video 456 nao teve seu processamento concluido!", thrown.getReason());

        verify(videoPort, times(1)).consultarVideoPorId("456");
    }
}
