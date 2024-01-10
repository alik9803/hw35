package ru.hogwarts.school5.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school5.model.Avatar;
import ru.hogwarts.school5.model.Student;

import java.io.IOException;
import java.nio.file.Path;

public interface AvatarService {
    String saveFile(MultipartFile file, Student student);

    void uploadAvatar(Long studentId, MultipartFile avatarfile) throws IOException;

    byte[] generateDataForDB(Path filePath) throws IOException;

    String getExtension(String fileName);

    Avatar findAvatar(Long studentId);

    Avatar getAvatarPage(Integer pageNumber, Integer pageSize);
}