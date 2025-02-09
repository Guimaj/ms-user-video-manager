package com.fiap.msuservideomanager.application.usecase;

import com.fiap.msuservideomanager.application.port.ArquivoPort;
import org.springframework.stereotype.Service;

@Service
public class BaixarArquivoUseCase {
    private final ArquivoPort arquivoPort;

    public BaixarArquivoUseCase(ArquivoPort arquivoPort) {
        this.arquivoPort = arquivoPort;
    }

    public byte[] baixarArquivo(String arquivoId) {
        return this.arquivoPort.baixarArquivo(arquivoId);
    }
}
