package com.example.fileprocessing.util;

import com.example.fileprocessing.model.ProcessedData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvProcessor {

    public static List<ProcessedData> processCsv(InputStream inputStream) {
        List<ProcessedData> processedDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // Skip header line
            reader.readLine();

            processedDataList = reader.lines()
                    .map(line -> {
                        String[] fields = line.split(",");
                        return new ProcessedData(fields[0], Integer.parseInt(fields[1]));
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV", e);
        }
        return processedDataList;
    }

    public static List<ProcessedData> processCsv(File inputFile, File outputFile) {
        try (InputStream inputStream = new FileInputStream(inputFile)) {
            return processCsv(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV files", e);
        }
    }
}
