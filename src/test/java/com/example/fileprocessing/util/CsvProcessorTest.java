package com.example.fileprocessing.util;

import com.example.fileprocessing.model.ProcessedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CsvProcessorTest {

    private File inputCsvFile;
    private File outputCsvFile;

    @BeforeEach
    public void setUp() throws IOException {
        inputCsvFile = File.createTempFile("input", ".csv");
        outputCsvFile = File.createTempFile("output", ".csv");

        try (FileWriter writer = new FileWriter(inputCsvFile)) {
            writer.write("name,age\nJohn,30\nJane,25");
        }
    }

    @Test
    public void testProcessCsv() throws IOException {
        List<ProcessedData> processedData = CsvProcessor.processCsv(inputCsvFile, outputCsvFile);
        assertNotNull(processedData);
        assertEquals(2, processedData.size());
        assertEquals("John", processedData.get(0).getName());
        assertEquals(30, processedData.get(0).getAge());
        assertEquals("Jane", processedData.get(1).getName());
        assertEquals(25, processedData.get(1).getAge());

        // Verify the output file content
        try (BufferedReader reader = new BufferedReader(new FileReader(outputCsvFile))) {
            String header = reader.readLine();
            String johnData = reader.readLine();
            String janeData = reader.readLine();
            assertEquals("\"name\",\"age\"", header);
            assertEquals("\"John\",\"30\"", johnData);
            assertEquals("\"Jane\",\"25\"", janeData);
        }
    }

    @Test
    public void testProcessCsvMalformedData() throws IOException {
        // Create malformed CSV input file
        File malformedCsvFile = File.createTempFile("malformed", ".csv");
        try (FileWriter writer = new FileWriter(malformedCsvFile)) {
            writer.write("name,age\nJohn,thirty\nJane,25");
        }

        // Expecting an exception to be thrown
        Exception exception = assertThrows(IOException.class, () -> {
            CsvProcessor.processCsv(malformedCsvFile, outputCsvFile);
        });
        assertTrue(exception.getMessage().contains("For input string: \"thirty\""));
    }
}
