package com.example.fileprocessing.service;

import com.example.fileprocessing.util.CsvProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public void processFile(MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File tempFile = convertMultipartFileToFile(file);
            File outputFile = File.createTempFile("output", ".csv");

            // Process the file
            CsvProcessor.processCsv(tempFile, outputFile);

            // Handle the processed data if needed (e.g., save to database, return to client, etc.)

            // Clean up temporary files
            tempFile.deleteOnExit();
            outputFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile(file.getOriginalFilename(), ".tmp");
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
