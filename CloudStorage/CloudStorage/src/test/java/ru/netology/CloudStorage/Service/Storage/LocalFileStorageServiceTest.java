package ru.netology.CloudStorage.Service.Storage;

import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileStorageServiceTest {

    private LocalFileStorageService storageService;
    private Path storagePath;

    @BeforeEach
    void setUp() throws IOException {
        storagePath = Files.createTempDirectory("test_uploads");
        storageService = new LocalFileStorageService();
        Files.createDirectories(storagePath);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(storagePath)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    void testSaveFile() throws IOException {
        String originalFilename = "test.txt";
        byte[] content = "Hello, World!".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", originalFilename, "text/plain", content);

        String savedFilename = storageService.saveFile(file);
        assertNotNull(savedFilename);
        Path savedFilePath = storagePath.resolve(savedFilename);
        assertTrue(Files.exists(savedFilePath));
        byte[] fileBytes = Files.readAllBytes(savedFilePath);
        assertArrayEquals(content, fileBytes);
    }

    @Test
    void testLoadFileAsBytes() throws IOException {
        String filename = "testload.txt";
        byte[] content = "Sample content".getBytes();
        Path filePath = storagePath.resolve(filename);
        Files.write(filePath, content);

        byte[] loadedBytes = storageService.loadFileAsBytes(filename);
        assertArrayEquals(content, loadedBytes);
    }

    @Test
    void testDeleteFile() throws IOException {
        String filename = "todelete.txt";
        Path filePath = storagePath.resolve(filename);
        Files.write(filePath, "delete me".getBytes());

        assertTrue(Files.exists(filePath));
        storageService.deleteFile(filename);
        assertFalse(Files.exists(filePath));
    }
}