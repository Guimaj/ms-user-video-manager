package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.port.LoginPort;
import com.fiap.msuservideomanager.application.usecase.CriaUsuarioUseCase;
import com.fiap.msuservideomanager.domain.exception.UsuarioExistenteException;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Usuario;
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
class CriaUsuarioUseCaseTest {

    @Mock
    private LoginPort loginPort;

    @InjectMocks
    private CriaUsuarioUseCase criaUsuarioUseCase;

    private Login login;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        login = new Login("user@example.com", "senha123");
        usuario = new Usuario("1", "user@example.com");
    }

    @Test
    void testCriarUsuario_DeveRetornarUsuario() {
        when(loginPort.cadastrarUsuario(any(Login.class))).thenReturn(usuario);

        Usuario resultado = criaUsuarioUseCase.criarUsuario(login);

        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getEmail(), resultado.getEmail());

        verify(loginPort, times(1)).cadastrarUsuario(any(Login.class));
    }

    @Test
    void testCriarUsuario_QuandoUsuarioJaExiste_DeveLancarExcecao() {
        when(loginPort.cadastrarUsuario(any(Login.class)))
                .thenThrow(new UsuarioExistenteException());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            criaUsuarioUseCase.criarUsuario(login);
        });

        assertEquals(400, thrown.getStatusCode().value());
        assertEquals("Usuario ja possui cadastro!", thrown.getReason());

        verify(loginPort, times(1)).cadastrarUsuario(any(Login.class));
    }
}
