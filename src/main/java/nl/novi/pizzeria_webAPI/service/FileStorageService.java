package nl.novi.pizzeria_webAPI.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private static final String STORAGE_DIRECTORY = "/Users/storage";

    public void saveFile(MultipartFile fileToSave) throws IOException {
        if (fileToSave == null) {
            throw new NullPointerException("fileToSave is null");
        }

        // Create the directory if it doesn't exist
        Path storagePath = Paths.get(STORAGE_DIRECTORY);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        // Security check for path traversal
        Path targetPath = storagePath.resolve(fileToSave.getOriginalFilename()).normalize();
        if (!targetPath.startsWith(storagePath)) {
            throw new SecurityException("Unsupported filename or path traversal attempt!");
        }

        Files.copy(fileToSave.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    }
}
