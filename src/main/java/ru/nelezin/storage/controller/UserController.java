package ru.nelezin.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nelezin.storage.dto.LoginRequest;
import ru.nelezin.storage.dto.MessageResponse;
import ru.nelezin.storage.dto.RegisterRequest;
import ru.nelezin.storage.dto.UserDto;
import ru.nelezin.storage.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
        UserDto userDto = userService.login(request);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        MessageResponse messageResponse = userService.register(request);
        return ResponseEntity.ok(messageResponse);
    }
}
