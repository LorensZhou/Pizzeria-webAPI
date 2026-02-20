package nl.novi.pizzeria_webAPI.controller;

import nl.novi.pizzeria_webAPI.service.FileStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FileManagerController {
    private FileStorageService fileStorageService;
    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());

    //injecteren dependencies
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

    @GetMapping("/download-file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String filename) {
        try {
            File file = fileStorageService.getDownloadFile(filename);

            InputStreamResource resource = new InputStreamResource(Files.newInputStream(file.toPath()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }catch(Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }

}
