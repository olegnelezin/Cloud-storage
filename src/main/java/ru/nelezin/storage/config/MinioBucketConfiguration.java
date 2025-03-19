package ru.nelezin.storage.config;

import jakarta.annotation.PostConstruct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.nelezin.storage.service.MinioBucketService;

@Getter
@RequiredArgsConstructor
@Configuration
public class MinioBucketConfiguration {

    @Value("${minio.bucket}")
    private String bucket;

    private final MinioBucketService minioService;

    @PostConstruct
    public void createBucket() {
        try {
            if (!minioService.isBucketExists(bucket)) {
                minioService.createBucket(bucket);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Bucket '" + bucket + "' is not created", e);
        }
    }
}
