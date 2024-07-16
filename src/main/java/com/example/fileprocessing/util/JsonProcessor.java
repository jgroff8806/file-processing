package com.example.fileprocessing.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.fileprocessing.model.ProcessedData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class JsonProcessor {

    public static List<ProcessedData> processJson(InputStream inputStream) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, new TypeReference<List<ProcessedData>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON input stream", e);
        }
    }

    // Existing method for File parameters
    public static void processJson(File inputFile, File outputFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new FileInputStream(inputFile)) {
            List<ProcessedData> data = objectMapper.readValue(inputStream, new TypeReference<List<ProcessedData>>() {});
            // Process and write to outputFile (implementation not shown)
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }
}

