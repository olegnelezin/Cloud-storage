package ru.nelezin.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nelezin.storage.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {
}
