package ru.nelezin.storage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nelezin.storage.dto.LoginRequest;
import ru.nelezin.storage.dto.RegisterRequest;
import ru.nelezin.storage.exception.AlreadyExistsException;
import ru.nelezin.storage.exception.PasswordNotMatchException;
import ru.nelezin.storage.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }


    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerRequest") RegisterRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.register(request);
        } catch (PasswordNotMatchException ex) {
            bindingResult.rejectValue("repeatPassword", "password.mismatch", ex.getMessage());
            return "register";
        } catch (AlreadyExistsException ex) {
            bindingResult.rejectValue("login", "login.exists", ex.getMessage());
            return "register";
        }

        return "redirect:/login";
    }
}
