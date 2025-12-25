package nl.novi.pizzeria_webAPI.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
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
            throw new FileNotFoundException("fileName cannot be empty");
        }

        // Create the directory if it doesn't exist
        Path storagePath = Paths.get(STORAGE_DIRECTORY);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        // Security check for path traversal
        Path targetPath = storagePath.resolve(fileToSave.getOriginalFilename()).normalize();
        if (!targetPath.startsWith(storagePath)) {
            throw new SecurityException("Access denied: Invalid file path");
        }

        Files.copy(fileToSave.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    }

    public File getDownloadFile(String fileName) throws IOException{
        if(fileName == null) {
            throw new FileNotFoundException("fileName cannot be empty");
        }
        File fileToDownload = new File(STORAGE_DIRECTORY + File.separator + fileName);

        //Security check
        if(!fileToDownload.getAbsolutePath().startsWith(STORAGE_DIRECTORY)){
            throw new SecurityException("Access denied: Invalid file path");
        }

        if(!fileToDownload.exists()){
            throw new FileNotFoundException("The file '" + fileName + "' does not exist on our server");
        }
        return fileToDownload;
    }
}
