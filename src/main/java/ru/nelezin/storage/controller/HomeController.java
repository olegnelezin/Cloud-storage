package ru.nelezin.storage.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nelezin.storage.entity.User;


@RequestMapping("/")
@Controller
public class HomeController {


    @GetMapping
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {

        }
        return "home";
    }
}
