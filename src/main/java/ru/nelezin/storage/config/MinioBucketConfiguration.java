package ru.nelezin.storage.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.nelezin.storage.service.MinioService;

@Setter
@Getter
@RequiredArgsConstructor
@Configuration
public class MinioBucketConfiguration {

    @Value("${minio.bucket-name}")
    private String bucketName;

    private final MinioService minioService;

    @PostConstruct
    public void createBucket() {
        try {
            if (!minioService.isBucketExists(bucketName)) {
                minioService.createBucket(bucketName);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Bucket '" + bucketName + "' is not created", e);
        }
    }
}
