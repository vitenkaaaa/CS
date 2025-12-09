package ru.netology.CloudStorage.Security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoints() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testProtectedEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection()); // редирект на страницу логина
    }

    @Test
    @WithMockUser(username = "user")
    public void testProtectedEndpointWithAuth() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }
}