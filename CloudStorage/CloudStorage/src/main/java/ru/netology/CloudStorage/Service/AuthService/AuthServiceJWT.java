package ru.netology.CloudStorage.Service.AuthService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.CloudStorage.Model.User;
import ru.netology.CloudStorage.Repository.UserRepository;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Service
public abstract class AuthServiceJWT implements AuthService {
    private final Map<String, String> activeSessions= new ConcurrentHashMap<>();
    private String secretKey = "your_secret_key";
    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String username, String password) {
        Optional<Optional<User>> userOpt = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOpt.isPresent()) {
            Optional<User> user = userOpt.get();
            if (checkPassword(password, user.get().getPassword())) {
                return "Успешный вход";
            } else {
                throw new RuntimeException("Неверный пароль");
            }
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
    }

    private boolean checkPassword(String rawPassword, String storedHashedPassword) {
        return rawPassword.equals(storedHashedPassword);
    }

    public boolean validateSession(String sessionId) {
        return activeSessions.containsKey(sessionId);
    }
    public String generateToken(String username) {
        return java.util.UUID.randomUUID().toString();
    }
}