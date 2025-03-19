package ru.nelezin.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nelezin.storage.service.FileService;

@RequiredArgsConstructor
@Controller
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam("file") MultipartFile file) {
        String login = userDetails.getUsername();
        fileService.addFile(login, file);
        return ResponseEntity.ok("");
    }
}
