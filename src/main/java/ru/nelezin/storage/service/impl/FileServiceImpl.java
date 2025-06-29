package ru.nelezin.storage.service.impl;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nelezin.storage.dto.FileDto;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.service.FileService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    String bucket;

    @Override
    public List<FileDto> getFilesByUser(User user) {
        String prefix = user.getLogin() + "/";
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucket)
                            .prefix(prefix)
                            .recursive(false)
                            .build()
            );
            List<FileDto> files = new ArrayList<>();
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName(); // e.g. "login/filename.ext"
                String fileNameWithExt = objectName.substring(prefix.length());
                String filename;
                String extension = "";
                int dotIndex = fileNameWithExt.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < fileNameWithExt.length() - 1) {
                    filename = fileNameWithExt.substring(0, dotIndex);
                    extension = fileNameWithExt.substring(dotIndex + 1);
                } else {
                    filename = fileNameWithExt;
                }
                long bytes = item.size();
                String human = (bytes / 1024) + "Kb";
                files.add(new FileDto(
                        filename,
                        extension,
                        human,
                        bytes
                ));
            }
            if (!files.isEmpty()) {
                files.remove(0);
            }
            return files;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file for user " + user.getLogin(), e);
        }
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
            throw new RuntimeException("Не удалось добавить файл");
        }
    }

    @Override
    public Resource loadAsResource(String filename, User user) {
        String objectName = user.getLogin() + "/" + filename;
        try {
            return new InputStreamResource(minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Не удалось скачать файл: " + filename, e);
        }
    }

    @Override
    public void deleteFile(String filename, User user) {
        String objectName = user.getLogin() + "/" + filename;
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Не удалось удалить файл: " + filename, e);
        }
    }
}
