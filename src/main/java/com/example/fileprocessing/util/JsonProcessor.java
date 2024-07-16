package com.example.fileprocessing.util;

import com.example.fileprocessing.model.ProcessedData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class JsonProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<ProcessedData> processJson(File inputFile) throws IOException {
        // Read and process the JSON file
        List<ProcessedData> processedData;
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            processedData = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, ProcessedData.class));
        }

        return processedData;
    }

    public static void writeJson(List<ProcessedData> processedData, File outputFile) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            objectMapper.writeValue(outputStream, processedData);
        }
    }
}
