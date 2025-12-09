package ru.netology.CloudStorage.Service.AuthService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.netology.CloudStorage.Model.User;
import ru.netology.CloudStorage.Repository.UserRepository;

import java.util.Optional;

public class AuthServiceJWTTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceJWT authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceJWT() {
            @Override
            public boolean validateToken(String token) {
                return false;
            }

            @Override
            public boolean logout(String sessionId) {
                return false;
            }
        };
    }

    @Test
    public void testLogin_Success() {
        String username = "testUser";
        String password = "password123";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(user);

        String result = authService.login(username, password);
        assertEquals("Успешный вход", result);
    }

    @Test
    public void testLogin_UserNotFound() {
        String username = "notExist";
        String password = "any";

        when(userRepository.findByUsername(username)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(username, password);
        });
        assertEquals("Пользователь не найден", exception.getMessage());
    }

    @Test
    public void testLogin_WrongPassword() {
        String username = "testUser";
        String password = "wrongPassword";

        User user = new User();
        user.setUsername(username);
        user.setPassword("correctPassword");

        when(userRepository.findByUsername(username)).thenReturn(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(username, password);
        });
        assertEquals("Неверный пароль", exception.getMessage());
    }

    @Test
    public void testValidateSession() {
        String sessionId = "session123";

        assertFalse(authService.validateSession(sessionId));


        AuthServiceJWT testInstance = new AuthServiceJWT() {
            {
                activeSessions.put(sessionId, "user");
            }
        };

        assertTrue(testInstance.validateSession(sessionId));
    }
}