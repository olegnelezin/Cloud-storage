package ru.nelezin.storage.service;

import ru.nelezin.storage.dto.LoginRequest;
import ru.nelezin.storage.dto.MessageResponse;
import ru.nelezin.storage.dto.RegisterRequest;
import ru.nelezin.storage.dto.UserDto;
import ru.nelezin.storage.entity.User;

public interface UserService {

    User getCurrentUser();

    UserDto login(LoginRequest request);

    MessageResponse register(RegisterRequest request);

    User getUserByLogin(String login);

    boolean isUserExistsByLogin(String login);

    void saveUser(User user);
}
