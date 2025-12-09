package ru.netology.CloudStorage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.CloudStorage.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    default boolean userExistsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    default User getUserByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

