package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final List<String> VALID_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file, String subFolder) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được rỗng");
        }
        if (!isValidImage(file)) {
            throw new IllegalArgumentException("File phải là ảnh (JPEG, PNG, GIF)");
        }

        // Tạo thư mục uploads và subFolder nếu chưa tồn tại
        Path subFolderPath = Paths.get(uploadDir, subFolder);
        Files.createDirectories(subFolderPath);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = subFolderPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + subFolder + "/" + fileName;
    }

    @Override
    public void deleteFileByUrl(String url) {
        if (url != null && url.startsWith("/uploads/")) {
            String relativePath = url.substring("/uploads/".length());
            Path filePath = Paths.get(uploadDir, relativePath);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi xóa file: " + url, e);
            }
        }
    }

    @Override
    public boolean isValidImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        String contentType = file.getContentType();
        return contentType != null && VALID_IMAGE_TYPES.contains(contentType);
    }
}