package com.example.fileprocessing.controller;

import com.example.fileprocessing.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String outputFilePath = fileService.processFile(file);
            model.addAttribute("message", "File processed successfully: " + outputFilePath);
        } catch (IOException e) {
            model.addAttribute("message", "Error processing file: " + e.getMessage());
        }
        return "result";
    }
}
