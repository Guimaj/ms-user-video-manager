package com.fiap.msuservideomanager.application.usecase;

import com.fiap.msuservideomanager.application.port.LoginPort;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CriaUsuarioUseCase {
    private final LoginPort loginPort;

    public CriaUsuarioUseCase(LoginPort loginPort) {
        this.loginPort = loginPort;
    }

    public Usuario criarUsuario(Login login) {
        return this.loginPort.cadastrarUsuario(login);
    }
}
