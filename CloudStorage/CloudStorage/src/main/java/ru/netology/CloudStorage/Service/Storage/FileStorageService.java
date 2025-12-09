package ru.netology.CloudStorage.Service.Storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String saveFile(MultipartFile file) throws IOException;
    byte[] loadFileAsBytes(String filename) throws IOException;
    void deleteFile(String filename) throws IOException;
}

