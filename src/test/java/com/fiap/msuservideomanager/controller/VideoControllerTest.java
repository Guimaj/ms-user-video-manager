package com.fiap.msuservideomanager.controller;

import com.fiap.msuservideomanager.application.usecase.*;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Url;
import com.fiap.msuservideomanager.domain.model.Video;
import com.fiap.msuservideomanager.entrypoint.controller.VideoController;
import com.fiap.msuservideomanager.entrypoint.dto.ArquivoDTO;
import com.fiap.msuservideomanager.entrypoint.dto.UrlDTO;
import com.fiap.msuservideomanager.entrypoint.dto.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class VideoControllerTest {

    @Mock
    private CadastrarVideoUseCase cadastrarVideoUseCase;

    @Mock
    private ConsultarVideoUseCase consultarVideoUseCase;

    @Mock
    private UrlVideoUseCase urlVideoUseCase;

    @Mock
    private ExtrairUsuarioTokenUseCase extrairUsuarioTokenUseCase;

    @Mock
    private BaixarArquivoUseCase baixarArquivoUseCase;

    @InjectMocks
    private VideoController videoController;

    private ArquivoDTO arquivoDTO;
    private Arquivo arquivo;
    private Url url;
    private Video video;
    private byte[] zipArquivo;

    @BeforeEach
    void setUp() {
        arquivoDTO = new ArquivoDTO("video.mp4", "mp4", "1024", "10");
        arquivo = new Arquivo("video.mp4", "mp4", "1024", "10");
        url = new Url("mocked-url", "123");
        video = new Video("123", "video.mp4", "mp4", "1024", "10");
        zipArquivo = new byte[]{1, 2, 3, 4};
    }

    @Test
    void testGeraUrlVideo_DeveRetornarUrlComStatus200() {
        when(extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("sub")).thenReturn("userId");
        when(extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("email")).thenReturn("user@example.com");
        when(urlVideoUseCase.geraUrlVideo(any(Arquivo.class), eq("userId"))).thenReturn(url);

        ResponseEntity<UrlDTO> response = videoController.geraUrlVideo(arquivoDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(url.getUrl(), response.getBody().getUrl());
        assertEquals(url.getId(), response.getBody().getIdArquivo());

        verify(cadastrarVideoUseCase, times(1)).cadastraVideo(any(Arquivo.class), eq("userId"), eq(url.getId()), eq("user@example.com"));
        verify(urlVideoUseCase, times(1)).geraUrlVideo(any(Arquivo.class), eq("userId"));
    }

    @Test
    void testConsultarVideosPorUsuario_DeveRetornarListaComStatus200() {
        when(extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("sub")).thenReturn("userId");
        when(consultarVideoUseCase.consultarVideosPorUsuario("userId")).thenReturn(List.of(video));

        ResponseEntity<List<VideoDTO>> response = videoController.consultarVideosPorUsuario();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(video.getNome(), response.getBody().get(0).getNome());

        verify(consultarVideoUseCase, times(1)).consultarVideosPorUsuario("userId");
    }

    @Test
    void testDownloadArquivoZip_DeveRetornarArquivoZipComStatus200() {
        when(consultarVideoUseCase.consultarVideoPorId("123")).thenReturn(video);
        when(baixarArquivoUseCase.baixarArquivo("123")).thenReturn(zipArquivo);

        ResponseEntity<byte[]> response = videoController.downloadArquivoZip("123");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(zipArquivo.length, response.getBody().length);

        verify(consultarVideoUseCase, times(1)).consultarVideoPorId("123");
        verify(baixarArquivoUseCase, times(1)).baixarArquivo("123");
    }

    @Test
    void testDownloadArquivoZip_QuandoVideoNaoExiste_DeveRetornar422() {
        when(consultarVideoUseCase.consultarVideoPorId("123"))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(422), "Erro ao tentar obter o vídeo"));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            videoController.downloadArquivoZip("123");
        });

        assertEquals(422, thrown.getStatusCode().value());
        assertEquals("Erro ao tentar obter o vídeo", thrown.getReason());

        verify(consultarVideoUseCase, times(1)).consultarVideoPorId("123");
        verify(baixarArquivoUseCase, times(0)).baixarArquivo(anyString()); // Não deve chamar baixarArquivo()
    }

    @Test
    void testDownloadArquivoZip_QuandoErroInterno_DeveRetornar500() {
        when(consultarVideoUseCase.consultarVideoPorId("123")).thenReturn(video);
        when(baixarArquivoUseCase.baixarArquivo("123"))
                .thenThrow(new RuntimeException("Erro inesperado ao baixar arquivo"));

        Exception thrown = assertThrows(RuntimeException.class, () -> {
            videoController.downloadArquivoZip("123");
        });

        assertEquals("Erro inesperado ao baixar arquivo", thrown.getMessage());

        verify(consultarVideoUseCase, times(1)).consultarVideoPorId("123");
        verify(baixarArquivoUseCase, times(1)).baixarArquivo("123");
    }
}
