package com.fiap.msuservideomanager.application.port;

import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Token;
import com.fiap.msuservideomanager.domain.model.Usuario;

public interface LoginPort {
    Usuario cadastrarUsuario(Login login);
    Token autenticarUsuario(Login login);
}
