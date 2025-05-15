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
import ru.nelezin.storage.exception.AlreadyExistsException;
import ru.nelezin.storage.exception.NotFoundException;
import ru.nelezin.storage.exception.PasswordNotMatchException;
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
            throw new PasswordNotMatchException("Неверный пароль");
        }
        return new UserDto(user.getId(), user.getLogin());
    }


    public User getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + login));
    }

    @Transactional
    @Override
    public MessageResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordNotMatchException("Пароли не совпадают");
        }

        if (isUserExistsByLogin(request.getLogin())) {
            throw new AlreadyExistsException("Пользователь с таким логином уже существует");
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
                () -> new NotFoundException("Пользователь с таким логином не найден"));
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
