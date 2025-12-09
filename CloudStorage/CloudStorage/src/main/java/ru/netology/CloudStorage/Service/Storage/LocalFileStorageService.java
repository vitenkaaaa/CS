package ru.netology.CloudStorage.Service.Storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalFileStorageService implements FileStorageService {

    final Path storageLocation;

    public LocalFileStorageService() {
        this.storageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory", e);
        }
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path targetLocation = this.storageLocation.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    @Override
    public byte[] loadFileAsBytes(String filename) throws IOException {
        Path filePath = storageLocation.resolve(filename).normalize();
        return Files.readAllBytes(filePath);
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        Path filePath = storageLocation.resolve(filename).normalize();
        Files.deleteIfExists(filePath);
    }
}
