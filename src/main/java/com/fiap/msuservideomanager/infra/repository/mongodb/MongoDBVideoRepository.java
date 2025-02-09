package com.fiap.msuservideomanager.infra.repository.mongodb;

import com.fiap.msuservideomanager.infra.repository.entity.VideoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoDBVideoRepository extends MongoRepository<VideoEntity, String> {
    List<VideoEntity> findByUsuarioId(String usuarioId);
}
