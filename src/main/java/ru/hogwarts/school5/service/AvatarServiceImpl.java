package ru.hogwarts.school5.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.PathMatcher;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school5.model.Avatar;
import ru.hogwarts.school5.model.Student;
import ru.hogwarts.school5.repository.AvatarRepository;
import ru.hogwarts.school5.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentRepository studentRepository,
                             AvatarRepository avatarRepository, PathMatcher path) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public String saveFile(MultipartFile file, Student student) {
        var dotIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.');
        var ext = file.getOriginalFilename().substring(dotIndex + 1);
        var path = avatarsDir + "/" + student.getId() + "_" + student.getName() + "." + ext;
        try (var in = file.getInputStream();
             var out = new FileOutputStream(path)) {
            in.transferTo(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public Avatar getAvatarPage(Integer pageNumber, Integer pageSize) {
        Pageable request = PageRequest.of(pageNumber, pageSize);
        return (Avatar) avatarRepository.findAll(request).getContent().get(0);
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarfile) throws IOException {
        Optional<Student> student = studentRepository.findById(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "_" + student.get().getName() + "." + getExtension(avatarfile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarfile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student.get());
        avatar.setFilePath(filePath.toString());
        avatar.setData(generateDataForDB(filePath));
        avatar.setFileSize(avatarfile.getSize());
        avatar.setMediaType(avatarfile.getContentType());

        avatarRepository.save(avatar);
    }

    @Override
    public byte[] generateDataForDB(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}