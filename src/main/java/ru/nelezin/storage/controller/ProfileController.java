package ru.nelezin.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.service.UserService;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "profile";
    }
}
