package com.fiap.msuservideomanager.application.usecase;

import com.fiap.msuservideomanager.application.port.ArquivoPort;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Url;
import org.springframework.stereotype.Service;

@Service
public class UrlVideoUseCase {
    private final ArquivoPort arquivoPort;

    public UrlVideoUseCase(ArquivoPort arquivoPort) {
        this.arquivoPort = arquivoPort;
    }

    public Url geraUrlVideo(Arquivo arquivo, String usuarioId) {
        return this.arquivoPort.gerarUrlArquivo(arquivo, usuarioId);
    }
}
