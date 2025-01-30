package ru.nelezin.storage.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nelezin.storage.service.MinioService;

@AllArgsConstructor
@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Override
    public void createBucket(String bucketName) throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    public boolean isBucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
    }
}
