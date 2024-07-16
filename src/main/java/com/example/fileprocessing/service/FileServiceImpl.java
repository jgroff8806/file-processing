package com.example.fileprocessing.service;

import com.example.fileprocessing.model.ProcessedData;
import com.example.fileprocessing.util.CsvProcessor;
import com.example.fileprocessing.util.JsonProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String processFile(MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File tempFile = convertMultipartFileToFile(file);
            File outputFile = File.createTempFile("output", ".csv");

            // Process the file
            List<ProcessedData> processedData = CsvProcessor.processCsv(tempFile, outputFile);

            // Handle the processed data if needed (e.g., save to database, return to client, etc.)
            logger.info("Processed data: {}", processedData);

            // Clean up temporary input file
            tempFile.deleteOnExit();

            // Return the output file path
            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Failed to convert MultipartFile to File", e);
            throw new RuntimeException("Error processing file", e);
        } catch (Exception e) {
            logger.error("Failed to process file", e);
            throw new RuntimeException("Error processing file", e);
        }
    }

    @Override
    public String processJsonFile(MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File tempFile = convertMultipartFileToFile(file);
            File outputFile = File.createTempFile("output", ".json");

            // Process the JSON file
            List<ProcessedData> processedData = JsonProcessor.processJson(tempFile);

            // Write the processed data to the output file
            JsonProcessor.writeJson(processedData, outputFile);

            // Log the processed data
            logger.info("Processed JSON data: {}", processedData);

            // Clean up temporary input file
            tempFile.deleteOnExit();

            // Return the output file path
            return outputFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Failed to convert MultipartFile to File", e);
            throw new RuntimeException("Error processing JSON file", e);
        } catch (Exception e) {
            logger.error("Failed to process JSON file", e);
            throw new RuntimeException("Error processing JSON file", e);
        }
    }

    protected File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile(file.getOriginalFilename(), ".tmp");
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
