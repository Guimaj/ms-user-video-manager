package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.port.LoginPort;
import com.fiap.msuservideomanager.application.usecase.LoginUsuarioUseCase;
import com.fiap.msuservideomanager.domain.exception.UsuarioOuSenhaInvalidoException;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUsuarioUseCaseTest {

    @Mock
    private LoginPort loginPort;

    @InjectMocks
    private LoginUsuarioUseCase loginUsuarioUseCase;

    private Login login;
    private Token token;

    @BeforeEach
    void setUp() {
        login = new Login("user@example.com", "senha123");
        token = new Token("mocked-jwt-token");
    }

    @Test
    void testLoginUsuario_DeveRetornarToken() {
        when(loginPort.autenticarUsuario(any(Login.class))).thenReturn(token);

        Token resultado = loginUsuarioUseCase.loginUsuario(login);

        assertNotNull(resultado);
        assertEquals(token.getJwt(), resultado.getJwt());

        verify(loginPort, times(1)).autenticarUsuario(any(Login.class));
    }

    @Test
    void testLoginUsuario_QuandoCredenciaisInvalidas_DeveLancarExcecao() {
        when(loginPort.autenticarUsuario(any(Login.class)))
                .thenThrow(new UsuarioOuSenhaInvalidoException());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            loginUsuarioUseCase.loginUsuario(login);
        });

        assertEquals(403, thrown.getStatusCode().value());
        assertEquals("Usuario ou senha invalido!", thrown.getReason());

        verify(loginPort, times(1)).autenticarUsuario(any(Login.class));
    }
}
