package com.example.fileprocessing.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void processFile(MultipartFile file);
}
