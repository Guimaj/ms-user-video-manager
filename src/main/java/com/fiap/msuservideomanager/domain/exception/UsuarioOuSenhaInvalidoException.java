package com.fiap.msuservideomanager.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsuarioOuSenhaInvalidoException extends ResponseStatusException {
    public UsuarioOuSenhaInvalidoException() {
        super(HttpStatus.FORBIDDEN, "Usuario ou senha invalido!");
    }
}
