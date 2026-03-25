package com.hacheery.accountbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    private final String uploadDir = "uploads";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("success", 0);
                response.put("message", "File is empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Create upload directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique filename
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;
            Path path = Paths.get(uploadDir, fileName);

            // Save the file
            Files.write(path, file.getBytes());

            // Build response (following EditorJS standard or similar)
            response.put("success", 1);
            Map<String, String> fileData = new HashMap<>();
            fileData.put("url", "http://localhost:8085/uploads/" + fileName);
            fileData.put("name", originalFileName);
            fileData.put("size", String.valueOf(file.getSize()));
            response.put("file", fileData);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", 0);
            response.put("message", "Could not upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
