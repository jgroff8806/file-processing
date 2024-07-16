package com.example.fileprocessing.service;

import com.example.fileprocessing.exception.FileProcessingException;
import com.example.fileprocessing.util.CsvProcessor;
import com.example.fileprocessing.util.JsonProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String processFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new FileProcessingException("File name is null");
        }

        File convFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }

        String outputFilePath = "output_" + fileName;
        if (fileName.endsWith(".csv")) {
            CsvProcessor.processCsv(convFile, new File(outputFilePath));
        } else if (fileName.endsWith(".json")) {
            JsonProcessor.processJson(convFile, new File(outputFilePath));
        } else {
            throw new FileProcessingException("Unsupported file format");
        }

        return outputFilePath;
    }
}
