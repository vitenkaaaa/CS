package ru.netology.CloudStorage.Сontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import ru.netology.CloudStorage.Service.Storage.FileStorageService;

import static org.mockito.Mockito.*;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

    @Test
    public void testUploadFile_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        when(fileStorageService.saveFile(any())).thenReturn("test.txt");

        mockMvc.perform(multipart("/files/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("test.txt"));
    }

    @Test
    public void testUploadFile_Error() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        when(fileStorageService.saveFile(any())).thenThrow(new IOException());

        mockMvc.perform(multipart("/files/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error saving file"));
    }

    @Test
    public void testDownloadFile_Success() throws Exception {
        byte[] data = "file content".getBytes();
        when(fileStorageService.loadFileAsBytes("test.txt")).thenReturn(data);

        mockMvc.perform(get("/files/download/test.txt"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.txt\""))
                .andExpect(content().bytes(data));
    }

    @Test
    public void testDownloadFile_NotFound() throws Exception {
        when(fileStorageService.loadFileAsBytes("notfound.txt")).thenThrow(new IOException());

        mockMvc.perform(get("/files/download/notfound.txt"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFile_Success() throws Exception {
        doNothing().when(fileStorageService).deleteFile("test.txt");

        mockMvc.perform(delete("/files/delete/test.txt"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFile_Error() throws Exception {
        doThrow(new IOException()).when(fileStorageService).deleteFile("test.txt");

        mockMvc.perform(delete("/files/delete/test.txt"))
                .andExpect(status().isInternalServerError());
    }
}