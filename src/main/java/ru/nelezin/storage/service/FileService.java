package ru.nelezin.storage.service;

import ru.nelezin.storage.dto.FileDto;
import ru.nelezin.storage.entity.User;

import java.util.List;

public interface FileService {

    List<FileDto> getFilesByUser(User user);
}
