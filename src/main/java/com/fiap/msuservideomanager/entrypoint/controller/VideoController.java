package com.fiap.msuservideomanager.entrypoint.controller;

import com.fiap.msuservideomanager.application.usecase.*;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Error;
import com.fiap.msuservideomanager.domain.model.Url;
import com.fiap.msuservideomanager.entrypoint.dto.ArquivoDTO;
import com.fiap.msuservideomanager.entrypoint.dto.UrlDTO;
import com.fiap.msuservideomanager.entrypoint.dto.VideoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    private final CadastrarVideoUseCase cadastrarVideoUseCase;
    private final ConsultarVideoUseCase consultarVideoUseCase;
    private final UrlVideoUseCase urlVideoUseCase;
    private final ExtrairUsuarioTokenUseCase extrairUsuarioTokenUseCase;
    private final BaixarArquivoUseCase baixarArquivoUseCase;

    public VideoController(CadastrarVideoUseCase cadastrarVideoUseCase,
                           UrlVideoUseCase urlVideoUseCase,
                           ExtrairUsuarioTokenUseCase extrairUsuarioTokenUseCase,
                           ConsultarVideoUseCase consultarVideoUseCase,
                           BaixarArquivoUseCase baixarArquivoUseCase) {
        this.cadastrarVideoUseCase = cadastrarVideoUseCase;
        this.urlVideoUseCase = urlVideoUseCase;
        this.extrairUsuarioTokenUseCase = extrairUsuarioTokenUseCase;
        this.consultarVideoUseCase = consultarVideoUseCase;
        this.baixarArquivoUseCase = baixarArquivoUseCase;
    }

    @Operation(
            description = "Gera a url para upload do arquivo e persiste os dados do arquivo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Url gerada com sucesso!")
            }
    )
    @PostMapping
    public ResponseEntity<UrlDTO> geraUrlVideo(@RequestBody ArquivoDTO arquivoDTO) {
        Arquivo arquivo = new Arquivo(arquivoDTO.getNome(), arquivoDTO.getTipo(), arquivoDTO.getTamanho(), arquivoDTO.getIntervalo());

        String usuarioId = this.extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("sub");
        String email  = this.extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("email");

        Url urlVideo = this.urlVideoUseCase.geraUrlVideo(arquivo, usuarioId);
        this.cadastrarVideoUseCase.cadastraVideo(arquivo, usuarioId, urlVideo.getId(), email);

        return ResponseEntity.ok(new UrlDTO(urlVideo.getUrl(), urlVideo.getId()));
    }

    @Operation(
            description = "Consulta os videos do usuario autenticado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Videos retornados com sucesso!")
            }
    )
    @GetMapping
    public ResponseEntity<List<VideoDTO>> consultarVideosPorUsuario() {
        String usuarioId = this.extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("sub");

        List<VideoDTO> videoDTOS = this.consultarVideoUseCase.consultarVideosPorUsuario(usuarioId)
                .stream()
                .map(video -> new VideoDTO(video.getNome(), video.getTipo(), video.getTamanho(), video.getStatus()))
                .toList();

        return ResponseEntity.ok().body(videoDTOS);
    }

    @Operation(
            description = "Realiza o download do arquivo zip gerado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Download feito com sucesso!"),
                    @ApiResponse(responseCode = "422",
                            description = "Erro ao tentar obbter o video!",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @GetMapping(value = "/{videoId}/download", produces = "application/zip")
    public ResponseEntity<byte[]> downloadArquivoZip(@PathVariable final String videoId) {

        this.consultarVideoUseCase.consultarVideoPorId(videoId);

        byte[] arquivo = this.baixarArquivoUseCase.baixarArquivo(videoId);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + videoId + "\"");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/zip");
        headers.setContentLength(arquivo.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(arquivo);
    }
}
