package com.fiap.msuservideomanager.repository;

import com.fiap.msuservideomanager.domain.enumerator.StatusEnum;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Video;
import com.fiap.msuservideomanager.infra.repository.VideoRepository;
import com.fiap.msuservideomanager.infra.repository.entity.VideoEntity;
import com.fiap.msuservideomanager.infra.repository.mongodb.MongoDBVideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class VideoRepositoryTest {

    @Mock
    private MongoDBVideoRepository videoRepository;

    @InjectMocks
    private VideoRepository videoRepositoryImpl;

    private Arquivo arquivo;
    private VideoEntity videoEntity;

    @BeforeEach
    void setUp() {
        arquivo = new Arquivo("video.mp4", "mp4", "10000", "10");
        videoEntity = new VideoEntity(
                "123",
                arquivo.getNome(),
                arquivo.getTipo(),
                arquivo.getTamanho(),
                arquivo.getIntervalo(),
                StatusEnum.PENDENTE.getDescricao(),
                "user@example.com",
                "userId"
        );
    }

    @Test
    void testCadastrarVideo_DeveSalvarEVideEntityERetornarVideo() {
        when(videoRepository.save(any(VideoEntity.class))).thenReturn(videoEntity);

        Video video = videoRepositoryImpl.cadastrarVideo(arquivo, "userId", "123", "user@example.com");

        assertNotNull(video);
        assertEquals(videoEntity.getCodigo(), video.getCodigo());
        assertEquals(videoEntity.getNome(), video.getNome());
        assertEquals(videoEntity.getTipo(), video.getTipo());
        assertEquals(videoEntity.getTamanho(), video.getTamanho());
        assertEquals(videoEntity.getStatus(), video.getStatus());

        verify(videoRepository, times(1)).save(any(VideoEntity.class));
    }

    @Test
    void testConsultarVideoPorId_QuandoVideoExiste_DeveRetornarVideo() {
        when(videoRepository.findById("123")).thenReturn(Optional.of(videoEntity));

        Video video = videoRepositoryImpl.consultarVideoPorId("123");

        assertNotNull(video);
        assertEquals(videoEntity.getCodigo(), video.getCodigo());
        assertEquals(videoEntity.getNome(), video.getNome());
        assertEquals(videoEntity.getTipo(), video.getTipo());
        assertEquals(videoEntity.getTamanho(), video.getTamanho());
        assertEquals(videoEntity.getStatus(), video.getStatus());

        verify(videoRepository, times(1)).findById("123");
    }

    @Test
    void testConsultarVideoPorId_QuandoVideoNaoExiste_DeveRetornarNull() {
        when(videoRepository.findById("999")).thenReturn(Optional.empty());
        Video video = videoRepositoryImpl.consultarVideoPorId("999");
        assertNull(video);
        verify(videoRepository, times(1)).findById("999");
    }

    @Test
    void testConsultarVideosPorUsuario_QuandoExistemVideos_DeveRetornarListaDeVideos() {
        when(videoRepository.findByUsuarioId("userId")).thenReturn(List.of(videoEntity));
        List<Video> videos = videoRepositoryImpl.consultarVideosPorUsuario("userId");

        assertNotNull(videos);
        assertFalse(videos.isEmpty());
        assertEquals(1, videos.size());
        assertEquals(videoEntity.getCodigo(), videos.get(0).getCodigo());

        verify(videoRepository, times(1)).findByUsuarioId("userId");
    }

    @Test
    void testConsultarVideosPorUsuario_QuandoNaoExistemVideos_DeveRetornarListaVazia() {
        when(videoRepository.findByUsuarioId("userId")).thenReturn(List.of());

        List<Video> videos = videoRepositoryImpl.consultarVideosPorUsuario("userId");

        assertNotNull(videos);
        assertTrue(videos.isEmpty());

        verify(videoRepository, times(1)).findByUsuarioId("userId");
    }
}
