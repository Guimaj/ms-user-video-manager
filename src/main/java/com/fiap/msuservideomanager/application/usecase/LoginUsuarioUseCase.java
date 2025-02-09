package com.fiap.msuservideomanager.application.usecase;

import com.fiap.msuservideomanager.application.port.LoginPort;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Token;
import org.springframework.stereotype.Service;

@Service
public class LoginUsuarioUseCase {
    private final LoginPort loginPort;

    public LoginUsuarioUseCase(LoginPort loginPort) {
        this.loginPort = loginPort;
    }

    public Token loginUsuario(Login login) {
        return this.loginPort.autenticarUsuario(login);
    }
}
