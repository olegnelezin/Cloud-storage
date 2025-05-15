package ru.nelezin.storage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nelezin.storage.dto.LoginRequest;
import ru.nelezin.storage.dto.MessageResponse;
import ru.nelezin.storage.dto.RegisterRequest;
import ru.nelezin.storage.dto.UserDto;
import ru.nelezin.storage.entity.User;
import ru.nelezin.storage.repository.UserRepository;
import ru.nelezin.storage.service.FolderService;
import ru.nelezin.storage.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final FolderService folderService;

    @Override
    public UserDto login(LoginRequest request) {
        User user = getUserByLogin(request.getLogin());
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Incorrect password");
        }
        return new UserDto(user.getId(), user.getLogin());
    }


    public User getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + login));
    }

    @Transactional
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
        folderService.createFolder(user);

        return new MessageResponse("You have been registered");
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new RuntimeException("User does not exists"));
    }

    @Override
    public boolean isUserExistsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
