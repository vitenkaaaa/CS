package ru.netology.CloudStorage.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateAndExtractToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);
        assertFalse(token.isEmpty());
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testValidateToken_Valid() {
        String token = jwtUtil.generateToken("validUser");
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    public void testValidateToken_Invalid() {
        String invalidToken = "invalid.token.here";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

}