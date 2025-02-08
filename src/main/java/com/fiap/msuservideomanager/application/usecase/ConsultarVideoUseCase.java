package com.fiap.msuservideomanager.application.usecase;

import com.fiap.msuservideomanager.application.port.VideoPort;
import com.fiap.msuservideomanager.domain.enumerator.StatusEnum;
import com.fiap.msuservideomanager.domain.exception.StatusInvalidoException;
import com.fiap.msuservideomanager.domain.exception.VideoNaoEncontradoException;
import com.fiap.msuservideomanager.domain.model.Video;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ConsultarVideoUseCase {
    private final VideoPort videoPort;

    public ConsultarVideoUseCase(VideoPort videoPort) {
        this.videoPort = videoPort;
    }

    public List<Video> consultarVideosPorUsuario(String usuarioid) {
        return this.videoPort.consultarVideosPorUsuario(usuarioid);
    }

    public Video consultarVideoPorId(String id) {
        Video video =  this.videoPort.consultarVideoPorId(id);

        if(ObjectUtils.isEmpty(video))
            throw new VideoNaoEncontradoException("Video " + id + " não encontrado!");

        if(!video.getStatus().equals(StatusEnum.CONCLUIDO.getDescricao()))
            throw new StatusInvalidoException("Video " + id + " nao teve seu processamento concluido!");

        return video;
    }
}
