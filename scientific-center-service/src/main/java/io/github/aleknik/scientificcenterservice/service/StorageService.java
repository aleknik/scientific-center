package io.github.aleknik.scientificcenterservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for file storage.
 */
public interface StorageService {

    void store(MultipartFile file, String id);

    byte[] load(String id);
}
