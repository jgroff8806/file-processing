package com.example.fileprocessing.util;

import com.example.fileprocessing.exception.FileProcessingException;
import com.example.fileprocessing.model.ProcessedData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonProcessor {
    public static void processJson(File inputFile, File outputFile) {
        ObjectMapper mapper = new ObjectMapper();
        List<ProcessedData> processedDataList = new ArrayList<>();
        try {
            String[] lines = mapper.readValue(inputFile, String[].class);
            for (String line : lines) {
                processedDataList.add(new ProcessedData(line.toUpperCase()));
            }
        } catch (IOException e) {
            throw new FileProcessingException("Error reading JSON file: " + e.getMessage());
        }

        try {
            mapper.writeValue(outputFile, processedDataList);
        } catch (IOException e) {
            throw new FileProcessingException("Error writing JSON file: " + e.getMessage());
        }
    }
}
