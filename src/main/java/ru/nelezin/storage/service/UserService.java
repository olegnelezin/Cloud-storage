package ru.nelezin.storage.service;

import ru.nelezin.storage.dto.LoginRequest;
import ru.nelezin.storage.dto.MessageResponse;
import ru.nelezin.storage.dto.RegisterRequest;
import ru.nelezin.storage.dto.UserDto;
import ru.nelezin.storage.entity.User;

public interface UserService {

    UserDto login(LoginRequest request);

    MessageResponse register(RegisterRequest request);

    User getUserByLoginAndPassword(String login, String password);

    boolean isUserExistsByLogin(String login);

    void saveUser(User user);
}
