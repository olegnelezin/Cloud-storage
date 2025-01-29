package ru.nelezin.storage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nelezin.storage.dto.LoginRequest;
import ru.nelezin.storage.dto.MessageResponse;
import ru.nelezin.storage.dto.RegisterRequest;
import ru.nelezin.storage.dto.UserDto;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.repository.UserRepository;
import ru.nelezin.storage.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto login(LoginRequest request) {
        User user = getUserByLoginAndPassword(request.getLogin(), request.getPassword());
        UserDto userDto = new UserDto(user.getId(), user.getLogin());
        return userDto;
    }

    @Override
    public MessageResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new RuntimeException("#");
        }

        if (isUserExistsByLogin(request.getLogin())) {
            throw new RuntimeException("#");
        }

        User user = User.builder()
                .login(request.getLogin())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();
        saveUser(user);

        return new MessageResponse("You have been registered");
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        String passwordHash = passwordEncoder.encode(password);
        return userRepository.findByLoginAndPasswordHash(login, passwordHash).orElseThrow(
                () -> new RuntimeException("f"));
    }

    @Override
    public boolean isUserExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
