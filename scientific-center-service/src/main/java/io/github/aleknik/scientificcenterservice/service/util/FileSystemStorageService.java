package io.github.aleknik.scientificcenterservice.service.util;

import io.github.aleknik.scientificcenterservice.controller.exception.BadRequestException;
import io.github.aleknik.scientificcenterservice.controller.exception.NotFoundException;
import io.github.aleknik.scientificcenterservice.service.util.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage-location}")
    private String path;

    @Override
    public void store(MultipartFile file, String id) {
        final String newUrl = String.format("%s/%s", path, id);
        final File newFile = new File(newUrl);
        try {
            if (newFile.exists()) {
                Files.delete(newFile.toPath());
            }
            Files.createFile(newFile.toPath());
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file");
        }

        try (final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
            final byte[] bytes = file.getBytes();
            stream.write(bytes);
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file");
        }
    }

    @Override
    public byte[] load(String id) {
        try {
            final File imgPath = new File(String.format("%s/%s", path, id));
            return Files.readAllBytes(imgPath.toPath());
        } catch (IOException e) {
            throw new NotFoundException("File not found");
        }
    }
}
