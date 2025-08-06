package com.example.rubiesfashionstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file, String subFolder) throws IOException;

    void deleteFileByUrl(String url);

    boolean isValidImage(MultipartFile file);
}
