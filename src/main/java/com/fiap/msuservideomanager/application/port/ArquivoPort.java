package com.fiap.msuservideomanager.application.port;

import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Url;

public interface ArquivoPort {
    Url gerarUrlArquivo(Arquivo arquivo, String usuarioId);
    byte[] baixarArquivo(String arquivoId);
}
