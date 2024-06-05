package com.example.santhuongmai.controller;

import com.example.santhuongmai.service.UploadImageCloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/image")
public class UploadImageController {

    private final UploadImageCloudinaryService uploadService;

    public UploadImageController(UploadImageCloudinaryService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam("files") MultipartFile[] files) throws IOException {
        return new ResponseEntity<>(uploadService.upload(files), HttpStatus.OK);
    }

    @DeleteMapping("{publicId}")
    public ResponseEntity<?> delete(@PathVariable("publicId") String publicId) {
        Map<String, Object> response = new HashMap<>();
        try {
            uploadService.delete(publicId);
            response.put("message", "Image deleted successful");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            response.put("message", "Error deleting file");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
