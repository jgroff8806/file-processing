package com.example.fileprocessing.util;

import com.example.fileprocessing.model.ProcessedData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;

class CsvProcessorTest {

    @Test
    void testProcessCsvValidData() {
        String csvData = "name,age\nJohn,30\nJane,25";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        List<ProcessedData> results = CsvProcessor.processCsv(inputStream);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("John", results.get(0).getName());
        assertEquals(30, results.get(0).getAge());
        assertEquals("Jane", results.get(1).getName());
        assertEquals(25, results.get(1).getAge());
    }

    @Test
    void testProcessCsvWithFiles() throws Exception {
        File inputFile = File.createTempFile("test", ".csv");
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("name,age\nJohn,30\nJane,25");
        }

        File outputFile = File.createTempFile("output", ".csv");

        List<ProcessedData> results = CsvProcessor.processCsv(inputFile, outputFile);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("John", results.get(0).getName());
        assertEquals(30, results.get(0).getAge());
        assertEquals("Jane", results.get(1).getName());
        assertEquals(25, results.get(1).getAge());

        inputFile.deleteOnExit();
        outputFile.deleteOnExit();
    }
}
