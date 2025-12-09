package ru.netology.CloudStorage.Service.FileService;


import org.springframework.web.multipart.MultipartFile;
import ru.netology.CloudStorage.Model.FileMeta;

import java.util.List;

public interface FileService {
    FileMeta uploadFile(Long userId, MultipartFile file);

    List<FileMeta> listFiles(Long userId);

    void deleteFile(Long fileId, Long userId);
}