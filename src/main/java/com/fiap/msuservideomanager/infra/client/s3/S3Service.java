package com.fiap.msuservideomanager.infra.client.s3;

import com.fiap.msuservideomanager.application.port.ArquivoPort;
import com.fiap.msuservideomanager.domain.model.Arquivo;
import com.fiap.msuservideomanager.domain.model.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service implements ArquivoPort {
    @Value("${aws.bucket-name}")
    private String bucketName;

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;

    public S3Service(S3Presigner s3Presigner, S3Client s3Client) {
        this.s3Presigner = s3Presigner;
        this.s3Client = s3Client;
    }

    @Override
    public Url gerarUrlArquivo(Arquivo arquivo, String usuarioId) {
        String uniqueId = UUID.randomUUID().toString();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("arquivo-id", uniqueId);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .metadata(metadata)
                .contentType(arquivo.getTipo())
                .contentLength(Long.valueOf(arquivo.getTamanho()))
                .key("videos/".concat(arquivo.getNome()))
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(
                builder -> builder.signatureDuration(Duration.ofHours(1))
                        .putObjectRequest(putObjectRequest)
                        .build()
        );
        return new Url(presignedRequest.url().toString(), uniqueId);
    }

    @Override
    public byte[] baixarArquivo(String arquivoId) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key("images/".concat(arquivoId).concat(".zip"))
                .build();
        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);

        return responseBytes.asByteArray();
    }
}
