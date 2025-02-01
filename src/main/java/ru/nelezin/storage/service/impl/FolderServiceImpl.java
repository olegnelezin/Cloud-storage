package ru.nelezin.storage.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nelezin.storage.config.MinioBucketConfiguration;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.service.FolderService;

import java.io.ByteArrayInputStream;

@RequiredArgsConstructor
@Service
public class FolderServiceImpl implements FolderService {

    private final MinioClient minioClient;

    private final MinioBucketConfiguration bucketConfiguration;

    @Override
    public void createFolder(User user) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketConfiguration.getBucket())
                            .object(user.getLogin() + "/")
                            .stream(new ByteArrayInputStream(new byte[0]), 0, -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error while creating folder for user");
        }
    }
}
