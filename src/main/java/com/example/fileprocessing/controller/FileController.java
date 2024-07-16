package com.example.fileprocessing.controller;

import com.example.fileprocessing.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            fileService.processFile(file);
            model.addAttribute("message", "File processed successfully");
            return "result";
        } catch (Exception e) {
            model.addAttribute("message", "Failed to process file: " + e.getMessage());
            return "result";
        }
    }
}
