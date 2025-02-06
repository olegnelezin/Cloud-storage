package ru.nelezin.storage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nelezin.storage.dto.FileDto;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/")
@Controller
public class HomeController {

    @GetMapping
    public String homePage(Model model) {
        List<FileDto> userFiles = new ArrayList<>();
        userFiles.add(new FileDto("1", "11", "111", "1111"));
        userFiles.add(new FileDto("2", "22", "222", "2222"));
        userFiles.add(new FileDto("3", "33", "333", "3333"));
        model.addAttribute("files", userFiles);
        return "home";
    }
}
