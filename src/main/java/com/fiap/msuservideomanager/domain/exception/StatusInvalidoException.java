package com.fiap.msuservideomanager.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StatusInvalidoException extends ResponseStatusException {
    public StatusInvalidoException(String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }
}
