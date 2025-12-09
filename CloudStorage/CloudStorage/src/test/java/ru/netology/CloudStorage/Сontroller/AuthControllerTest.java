package ru.netology.CloudStorage.Сontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.netology.CloudStorage.Service.AuthService.AuthService;
import ru.netology.CloudStorage.Login.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testLogin_Success() throws Exception {
        String username = "user1";
        String password = "pass";
        String sessionId = "session123";

        when(authService.login(username, password)).thenReturn(sessionId);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(sessionId));
    }

    @Test
    public void testLogin_Unauthorized() throws Exception {
        String username = "user1";
        String password = "wrong";

        when(authService.login(username, password)).thenThrow(new IllegalArgumentException());

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}