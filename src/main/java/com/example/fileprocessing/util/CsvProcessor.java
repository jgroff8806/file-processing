package com.example.fileprocessing.util;

import com.example.fileprocessing.exception.FileProcessingException;
import com.example.fileprocessing.model.ProcessedData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvProcessor {
    public static void processCsv(File inputFile, File outputFile) {
        List<ProcessedData> processedDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                processedDataList.add(new ProcessedData(line.toUpperCase()));
            }
        } catch (IOException e) {
            throw new FileProcessingException("Error reading CSV file: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (ProcessedData data : processedDataList) {
                bw.write(data.getData());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FileProcessingException("Error writing CSV file: " + e.getMessage());
        }
    }
}
