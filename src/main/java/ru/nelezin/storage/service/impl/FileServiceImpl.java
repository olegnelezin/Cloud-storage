package ru.nelezin.storage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nelezin.storage.dto.FileDto;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.service.FileService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<FileDto> getFilesByUser(User user) {
        return null;
    }
}
