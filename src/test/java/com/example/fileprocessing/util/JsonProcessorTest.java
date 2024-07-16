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
public class JsonProcessorTest {

    private File validJsonFile;
    private File invalidJsonFile;
    private File outputJsonFile;

    @BeforeEach
    public void setUp() throws IOException {
        validJsonFile = File.createTempFile("valid", ".json");
        invalidJsonFile = File.createTempFile("invalid", ".json");
        outputJsonFile = File.createTempFile("output", ".json");

        try (FileWriter writer = new FileWriter(validJsonFile)) {
            writer.write("[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]");
        }

        try (FileWriter writer = new FileWriter(invalidJsonFile)) {
            writer.write("[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",]"); // corrected malformed JSON
        }
    }

    @Test
    public void testProcessJson() throws IOException {
        List<ProcessedData> processedData = JsonProcessor.processJson(validJsonFile);
        assertNotNull(processedData);
        assertEquals(2, processedData.size());
        assertEquals("John", processedData.get(0).getName());
        assertEquals(30, processedData.get(0).getAge());
        assertEquals("Jane", processedData.get(1).getName());
        assertEquals(25, processedData.get(1).getAge());

        // Write the processed data to the output file
        JsonProcessor.writeJson(processedData, outputJsonFile);

        // Verify the output file content
        try (BufferedReader reader = new BufferedReader(new FileReader(outputJsonFile))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.contains("\"name\":\"John\""));
            assertTrue(line.contains("\"age\":30"));
            assertTrue(line.contains("\"name\":\"Jane\""));
            assertTrue(line.contains("\"age\":25"));
        }
    }

    @Test
    public void testProcessJsonMalformedData() {
        Exception exception = assertThrows(IOException.class, () -> {
            JsonProcessor.processJson(invalidJsonFile);
        });
        assertTrue(exception.getMessage().contains("Unexpected character"));
    }
}
