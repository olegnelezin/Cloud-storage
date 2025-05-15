package ru.nelezin.storage.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.nelezin.storage.dto.FileDto;
import ru.nelezin.storage.entity.User;

import java.util.List;

public interface FileService {

    List<FileDto> getFilesByUser(User user);

    void addFile(String login, MultipartFile file);

    Resource loadAsResource(String filename, User user);

    void deleteFile(String filename, User user);
}
