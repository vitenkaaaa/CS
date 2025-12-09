package ru.netology.CloudStorage.Service.UserService;

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

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        String username = "testUser";
        String rawPassword = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = userService.registerUser(username, rawPassword);
        assertTrue(result);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UserExists() {
        String username = "existingUser";
        String rawPassword = "password123";

        User existingUser = new User();
        existingUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        boolean result = userService.registerUser(username, rawPassword);
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testValidateUser_Valid() {
        String username = "user1";
        String rawPassword = "pass";

        User user = new User();
        user.setUsername(username);
        String encodedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(rawPassword);
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        boolean isValid = userService.validateUser(username, rawPassword);
        assertTrue(isValid);
    }

    @Test
    public void testValidateUser_InvalidPassword() {
        String username = "user2";
        String rawPassword = "wrongpass";

        User user = new User();
        user.setUsername(username);
        String encodedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("correctpass");
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        boolean isValid = userService.validateUser(username, rawPassword);
        assertFalse(isValid);
    }

    @Test
    public void testAuthenticate_Success() {
        String username = "authUser";
        String rawPassword = "mypassword";

        User user = new User();
        user.setUsername(username);
        String encodedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(rawPassword);
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.authenticate(username, rawPassword);
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        String username = "nonexistent";
        String password = "any";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.authenticate(username, password);
        });
    }

    @Test
    public void testAuthenticate_WrongPassword() {
        String username = "user";
        String password = "wrong";

        User user = new User();
        user.setUsername(username);
        String encodedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("correct");
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> {
            userService.authenticate(username, password);
        });
    }
}