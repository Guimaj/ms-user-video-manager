package com.fiap.msuservideomanager.entrypoint.controller;

import com.fiap.msuservideomanager.application.usecase.CriaUsuarioUseCase;
import com.fiap.msuservideomanager.application.usecase.LoginUsuarioUseCase;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Token;
import com.fiap.msuservideomanager.domain.model.Usuario;
import com.fiap.msuservideomanager.entrypoint.dto.LoginDTO;
import com.fiap.msuservideomanager.entrypoint.dto.TokenDTO;
import com.fiap.msuservideomanager.entrypoint.dto.UsuarioDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private LoginUsuarioUseCase loginUsuarioUseCase;

    @Mock
    private CriaUsuarioUseCase criaUsuarioUseCase;

    @InjectMocks
    private LoginController loginController;

    private LoginDTO loginDTO;
    private Usuario usuario;
    private Token token;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO("user@example.com", "senha123");
        usuario = new Usuario("1", "user@example.com");
        token = new Token("mocked-jwt-token");
    }

    @Test
    void testCadastro_DeveRetornarUsuarioCriado_ComStatus201() {
        when(criaUsuarioUseCase.criarUsuario(any(Login.class))).thenReturn(usuario);

        ResponseEntity<UsuarioDTO> response = loginController.cadastro(loginDTO);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(usuario.getId(), response.getBody().getId());
        assertEquals(usuario.getEmail(), response.getBody().getEmail());

        verify(criaUsuarioUseCase, times(1)).criarUsuario(any(Login.class));
    }

    @Test
    void testCadastro_QuandoUsuarioJaExiste_DeveRetornar400() {
        when(criaUsuarioUseCase.criarUsuario(any(Login.class)))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(400), "Usuário já existe"));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            loginController.cadastro(loginDTO);
        });

        assertEquals(400, thrown.getStatusCode().value());
        assertEquals("Usuário já existe", thrown.getReason());

        verify(criaUsuarioUseCase, times(1)).criarUsuario(any(Login.class));
    }

    @Test
    void testLogin_DeveRetornarToken_ComStatus200() {
        when(loginUsuarioUseCase.loginUsuario(any(Login.class))).thenReturn(token);

        ResponseEntity<TokenDTO> response = loginController.login(loginDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(token.getJwt(), response.getBody().getJwt());

        verify(loginUsuarioUseCase, times(1)).loginUsuario(any(Login.class));
    }

    @Test
    void testLogin_QuandoCredenciaisInvalidas_DeveRetornar403() {
        when(loginUsuarioUseCase.loginUsuario(any(Login.class)))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(403), "Email ou senha inválidos"));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            loginController.login(loginDTO);
        });

        assertEquals(403, thrown.getStatusCode().value());
        assertEquals("Email ou senha inválidos", thrown.getReason());

        verify(loginUsuarioUseCase, times(1)).loginUsuario(any(Login.class));
    }

    @Test
    void testLogin_QuandoErroInterno_DeveRetornar500() {
        when(loginUsuarioUseCase.loginUsuario(any(Login.class)))
                .thenThrow(new RuntimeException("Erro inesperado no login"));

        Exception thrown = assertThrows(RuntimeException.class, () -> {
            loginController.login(loginDTO);
        });

        assertEquals("Erro inesperado no login", thrown.getMessage());

        verify(loginUsuarioUseCase, times(1)).loginUsuario(any(Login.class));
    }
}
