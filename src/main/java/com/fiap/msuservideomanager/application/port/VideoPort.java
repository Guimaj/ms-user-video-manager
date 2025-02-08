package com.fiap.msuservideomanager.application.port;

import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Video;

import java.util.List;

public interface VideoPort {
    Video cadastrarVideo(Arquivo arquivo, String usuarioId, String codigo, String email);
    Video consultarVideoPorId(String id);
    List<Video> consultarVideosPorUsuario(String usuarioId);
}
