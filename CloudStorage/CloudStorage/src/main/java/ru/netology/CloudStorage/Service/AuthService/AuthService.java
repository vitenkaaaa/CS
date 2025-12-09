package ru.netology.CloudStorage.Service.AuthService;

public interface AuthService {
    String login(String username, String password);
    boolean validateToken(String token);

    boolean validateSession(String sessionId);

    boolean logout(String sessionId);
}
