package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.port.ArquivoPort;
import com.fiap.msuservideomanager.application.usecase.BaixarArquivoUseCase;
import com.fiap.msuservideomanager.domain.exception.VideoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BaixarArquivoUseCaseTest {

    @Mock
    private ArquivoPort arquivoPort;

    @InjectMocks
    private BaixarArquivoUseCase baixarArquivoUseCase;

    private byte[] arquivoBytes;

    @BeforeEach
    void setUp() {
        arquivoBytes = new byte[]{1, 2, 3, 4};
    }

    @Test
    void testBaixarArquivo_DeveRetornarArrayDeBytes() {
        when(arquivoPort.baixarArquivo("123")).thenReturn(arquivoBytes);

        byte[] resultado = baixarArquivoUseCase.baixarArquivo("123");

        assertNotNull(resultado);
        assertArrayEquals(arquivoBytes, resultado);

        verify(arquivoPort, times(1)).baixarArquivo("123");
    }

    @Test
    void testBaixarArquivo_QuandoArquivoNaoExiste_DeveLancarExcecao() {
        when(arquivoPort.baixarArquivo("999"))
                .thenThrow(new VideoNaoEncontradoException("Video nao encontrado!"));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            baixarArquivoUseCase.baixarArquivo("999");
        });

        assertEquals(404, thrown.getStatusCode().value());
        assertEquals("Video nao encontrado!", thrown.getReason());

        verify(arquivoPort, times(1)).baixarArquivo("999");
    }
}
