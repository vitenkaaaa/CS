package ru.netology.CloudStorage.Сontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.CloudStorage.Login.LoginRequest;
import ru.netology.CloudStorage.Logout.LogoutRequest;
import ru.netology.CloudStorage.Login.LoginResponse;
import ru.netology.CloudStorage.Model.User;
import ru.netology.CloudStorage.Service.AuthService.AuthService;
import ru.netology.CloudStorage.Service.UserService.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        boolean success = userService.registerUser(user.getUsername(), user.getPassword());
        return success ? "Регистрация прошла успешно" : "Пользователь уже существует";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            String sessionId = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(sessionId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}






