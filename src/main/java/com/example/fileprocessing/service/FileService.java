package com.example.fileprocessing.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String processFile(MultipartFile file);
    String processJsonFile(MultipartFile file);
}
