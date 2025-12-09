package ru.netology.CloudStorage.Service.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.CloudStorage.Model.FileMeta;
import ru.netology.CloudStorage.Repository.FileRepository;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileMeta uploadFile(Long userId, MultipartFile file) {
        logger.info("Начинаю загрузку файла для пользователя {}", userId);
        try {
            FileMeta fileMeta = new FileMeta(1l, "file1.txt");
            fileMeta.setFilename(file.getOriginalFilename());
            fileMeta.setId(userId);
            return fileRepository.save(fileMeta);
        } catch (Exception e) {
            logger.error("Ошибка при загрузке файла", e);
        }
        return null;
    }

    @Override
    public List<FileMeta> listFiles(Long userId) {
        return fileRepository.findByUserId(userId);
    }

    @Override
    public void deleteFile(Long fileId, Long userId) {
        logger.info("Попытка удалить файл с id " + fileId + " для пользователя " + userId);
        try {
            logger.info("Файл с id" + fileId + " успешно удален");
        } catch (Exception e) {
            logger.error("Ошибка при удалении файла с id" + fileId + e);
        }
    }
}
