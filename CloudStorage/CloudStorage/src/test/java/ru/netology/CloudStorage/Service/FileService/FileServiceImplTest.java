package ru.netology.CloudStorage.Service.FileService;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import ru.netology.CloudStorage.Model.FileMeta;
import ru.netology.CloudStorage.Repository.FileRepository;

import java.util.Arrays;
import java.util.List;

public class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFile_Success() {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "content".getBytes());

        Long userId = 1L;
        FileMeta savedMeta = new FileMeta(1l, "file1.txt");
        savedMeta.setFilename("test.txt");
        savedMeta.setId(userId);

        when(fileRepository.save(any(FileMeta.class))).thenReturn(savedMeta);

        FileMeta result = fileService.uploadFile(userId, multipartFile);
        assertNotNull(result);
        assertEquals("test.txt", result.getFilename());
        verify(fileRepository).save(any(FileMeta.class));
    }

    @Test
    public void testUploadFile_Exception() {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "content".getBytes());

        Long userId = 1L;

        when(fileRepository.save(any(FileMeta.class))).thenThrow(new RuntimeException("DB error"));

        FileMeta result = fileService.uploadFile(userId, multipartFile);
        assertNull(result);
    }

    @Test
    public void testListFiles() {
        Long userId = 2L;
        List<FileMeta> files = Arrays.asList(
                new FileMeta(1l, "file1.txt"),
                new FileMeta(2L, "file2.txt")
        );

        when(fileRepository.findByUserId(userId)).thenReturn(files);

        List<FileMeta> result = fileService.listFiles(userId);
        assertEquals(2, result.size());
        verify(fileRepository).findByUserId(userId);
    }

    @Test
    public void testDeleteFile() {
        Long fileId = 10L;
        Long userId = 3L;
        fileService.deleteFile(fileId, userId);
    }
}