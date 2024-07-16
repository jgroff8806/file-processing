package com.example.fileprocessing.controller;

import com.example.fileprocessing.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            logger.info("Received file for processing: {}", file.getOriginalFilename());
            String outputPath = fileService.processFile(file);
            model.addAttribute("message", "File processed successfully");
            model.addAttribute("downloadLink", "/download?file=" + URLEncoder.encode(outputPath, StandardCharsets.UTF_8.toString()));
            logger.info("File processed successfully");
            return "result";
        } catch (Exception e) {
            model.addAttribute("message", "Failed to process file: " + e.getMessage());
            logger.error("Failed to process file", e);
            return "result";
        }
    }

    @PostMapping("/uploadJson")
    public String handleJsonFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            logger.info("Received JSON file for processing: {}", file.getOriginalFilename());
            String outputPath = fileService.processJsonFile(file);
            model.addAttribute("message", "JSON file processed successfully");
            model.addAttribute("downloadLink", "/download?file=" + URLEncoder.encode(outputPath, StandardCharsets.UTF_8.toString()));
            logger.info("JSON file processed successfully");
            return "result";
        } catch (Exception e) {
            model.addAttribute("message", "Failed to process JSON file: " + e.getMessage());
            logger.error("Failed to process JSON file", e);
            return "result";
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Resource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            logger.error("Failed to download file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
