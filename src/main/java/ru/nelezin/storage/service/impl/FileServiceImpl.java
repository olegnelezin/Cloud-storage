package ru.nelezin.storage.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nelezin.storage.config.MinioBucketConfiguration;
import ru.nelezin.storage.dto.FileDto;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.service.FileService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    String bucket;

    @Override
    public List<FileDto> getFilesByUser(User user) {
        return null;
    }

    public void addFile(String login, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(login + "/" + file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("#");
        }
    }
}
