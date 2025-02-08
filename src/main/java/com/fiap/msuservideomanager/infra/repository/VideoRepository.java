package com.fiap.msuservideomanager.infra.repository;

import com.fiap.msuservideomanager.application.port.VideoPort;
import com.fiap.msuservideomanager.domain.enumerator.StatusEnum;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Video;
import com.fiap.msuservideomanager.infra.repository.entity.VideoEntity;
import com.fiap.msuservideomanager.infra.repository.mongodb.MongoDBVideoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepository implements VideoPort {

    private final MongoDBVideoRepository videoRepository;

    public VideoRepository(MongoDBVideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video cadastrarVideo(Arquivo arquivo, String usuarioId, String codigo, String email) {
        VideoEntity videoSalvo = this.videoRepository.save(new VideoEntity(codigo,
                arquivo.getNome(),
                arquivo.getTipo(),
                arquivo.getTamanho(),
                arquivo.getIntervalo(),
                StatusEnum.PENDENTE.getDescricao(),
                email,
                usuarioId));
        return new Video(videoSalvo.getCodigo(), videoSalvo.getNome(), videoSalvo.getTipo(), videoSalvo.getTamanho(), videoSalvo.getStatus());
    }

    @Override
    public Video consultarVideoPorId(String id) {
        return this.videoRepository.findById(id)
                .map(videoEntity -> new Video(videoEntity.getCodigo(), videoEntity.getNome(), videoEntity.getTipo(), videoEntity.getTamanho(), videoEntity.getStatus()))
                .orElse(null);
    }

    @Override
    public List<Video> consultarVideosPorUsuario(String usuarioId) {
        return this.videoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(videoEntity -> new Video(videoEntity.getCodigo(), videoEntity.getNome(), videoEntity.getTipo(), videoEntity.getTamanho(), videoEntity.getStatus()))
                .toList();
    }
}
