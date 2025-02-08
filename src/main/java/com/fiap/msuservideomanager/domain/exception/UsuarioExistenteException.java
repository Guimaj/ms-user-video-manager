package com.fiap.msuservideomanager.domain.exception;

public class UsuarioExistenteException extends AlreadyExistsException {
    public UsuarioExistenteException() {
        super("Usuario ja possui cadastro!");
    }
}
