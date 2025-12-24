package nl.novi.pizzeria_webAPI.controller;

import nl.novi.pizzeria_webAPI.service.FileStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileManagerController {
    private FileStorageService fileStorageService;
    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());

    //inject dependencies
    public FileManagerController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("upload-file")
    public String uploadfile(@RequestParam("file") MultipartFile file) {
        try {
            this.fileStorageService.saveFile(file);
            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception during upload", e);
            return "Upload failed: " + e.getMessage();
        } catch (SecurityException e) {
            return "Security error: " + e.getMessage();
        }
    }

}
