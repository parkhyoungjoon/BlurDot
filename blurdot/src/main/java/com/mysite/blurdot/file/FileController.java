package com.mysite.blurdot.file;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {
    private final String uploadDir;

    public FileController(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("file") MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                File dest = new File(uploadDir, file.getOriginalFilename());
                file.transferTo(dest);
            }
            return ResponseEntity.ok("Files uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        File file = new File(uploadDir, filename);
        if (file.exists() && file.delete()) {
            return ResponseEntity.ok("File deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        File dir = new File(uploadDir);
        String[] files = dir.list();
        return ResponseEntity.ok(Arrays.asList(files != null ? files : new String[0]));
    }
}