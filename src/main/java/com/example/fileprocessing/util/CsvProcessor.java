package com.example.fileprocessing.util;

import com.example.fileprocessing.model.ProcessedData;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvProcessor {

    public static List<ProcessedData> processCsv(File inputFile, File outputFile) throws IOException {
        List<ProcessedData> processedData = new ArrayList<>();

        // Reading and processing the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equals("name")) { // Skip the header
                    try {
                        String name = data[0];
                        int age = Integer.parseInt(data[1]);
                        processedData.add(new ProcessedData(name, age));
                        writer.writeNext(new String[]{name, String.valueOf(age)});
                    } catch (NumberFormatException e) {
                        throw new IOException("For input string: \"" + data[1] + "\"");
                    }
                } else {
                    writer.writeNext(data); // Write header
                }
            }
        }

        return processedData;
    }
}
