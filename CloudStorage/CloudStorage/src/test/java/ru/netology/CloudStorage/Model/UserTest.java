package ru.netology.CloudStorage.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testCreateUser() {
        User user = new User("testUser", "password123");
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();

        user.setId(5L);
        user.setUsername("newUser");
        user.setPassword("newPassword");

        assertEquals(5L, user.getId());
        assertEquals("newUser", user.getUsername());
        assertEquals("newPassword", user.getPassword());
    }
}