package ru.netology.CloudStorage.Model;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileMetaTest {

    @Test
    public void testCreateFileMeta() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        LocalDateTime now = LocalDateTime.now();

        FileMeta fileMeta = new FileMeta("file.txt", "/uploads/file.txt", now, user);

        assertEquals("file.txt", fileMeta.getFilename());
        assertEquals("/uploads/file.txt", fileMeta.getFilepath());
        assertEquals(now, fileMeta.getUploadDate());
        assertEquals(user, fileMeta.getUser());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setId(2L);
        user.setUsername("anotherUser");

        FileMeta fileMeta = new FileMeta(1l, "file1.txt");

        fileMeta.setId(10L);
        fileMeta.setFilename("doc.pdf");
        fileMeta.setFilepath("/docs/doc.pdf");
        LocalDateTime uploadTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        fileMeta.setUploadDate(uploadTime);
        fileMeta.setUser(user);

        assertEquals(10L, fileMeta.getId());
        assertEquals("doc.pdf", fileMeta.getFilename());
        assertEquals("/docs/doc.pdf", fileMeta.getFilepath());
        assertEquals(uploadTime, fileMeta.getUploadDate());
        assertEquals(user, fileMeta.getUser());
    }
}