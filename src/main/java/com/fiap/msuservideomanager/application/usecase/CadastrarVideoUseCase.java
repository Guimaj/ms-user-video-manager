package com.fiap.msuservideomanager.application.usecase;

import com.fiap.msuservideomanager.application.port.VideoPort;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Video;
import org.springframework.stereotype.Service;

@Service
public class CadastrarVideoUseCase {
    private final VideoPort videoPort;

    public CadastrarVideoUseCase(VideoPort videoPort) {
        this.videoPort = videoPort;
    }

    public Video cadastraVideo(Arquivo arquivo, String usuarioId, String codigo, String email) {
        return this.videoPort.cadastrarVideo(arquivo, usuarioId, codigo, email);
    }
}
