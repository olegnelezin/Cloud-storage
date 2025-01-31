package ru.nelezin.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nelezin.storage.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}
