package ru.netology.CloudStorage.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.CloudStorage.Model.FileMeta;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileMeta, Long> {
    List<FileMeta> findByUserId(Long userId);
}
