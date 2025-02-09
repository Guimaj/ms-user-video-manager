package com.fiap.msuservideomanager.domain.exception;

public class VideoNaoEncontradoException extends NotFoundException{
    public VideoNaoEncontradoException(String reason) {
        super(reason);
    }
}
