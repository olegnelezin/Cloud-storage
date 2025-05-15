package ru.nelezin.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nelezin.storage.dto.FileDto;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.service.FileService;
import ru.nelezin.storage.service.UserService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class HomeController {

    private final FileService fileService;

    private final UserService userService;

    @GetMapping
    public String homePage(Model model) {
        List<FileDto> userFiles = fileService.getFilesByUser(userService.getCurrentUser());
        model.addAttribute("files", userFiles);
        return "home";
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = fileService.loadAsResource(filename, userService.getCurrentUser());
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                .body(file);
    }


    @PostMapping("/delete")
    public String deleteFile(@RequestParam String filename) {
        fileService.deleteFile(filename, userService.getCurrentUser());
        return "redirect:/";
    }
}
