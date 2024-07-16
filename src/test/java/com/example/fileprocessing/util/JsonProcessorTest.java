package com.example.fileprocessing.util;

import com.example.fileprocessing.model.ProcessedData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

class JsonProcessorTest {

    @Test
    void testProcessJsonValidData() {
        String jsonData = "[{\"name\":\"John\", \"age\":30}, {\"name\":\"Jane\", \"age\":25}]";
        InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());

        List<ProcessedData> result = JsonProcessor.processJson(inputStream);

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals(30, result.get(0).getAge());
        assertEquals("Jane", result.get(1).getName());
        assertEquals(25, result.get(1).getAge());
    }

    @Test
    void testProcessJsonMalformedData() {
        String badJsonData = "[{\"name\":\"John\", \"age\":30,]";
        InputStream inputStream = new ByteArrayInputStream(badJsonData.getBytes());

        assertThrows(RuntimeException.class, () -> JsonProcessor.processJson(inputStream));
    }

    @Test
    void testProcessJsonEmptyData() {
        String emptyJson = "[]";
        InputStream inputStream = new ByteArrayInputStream(emptyJson.getBytes());

        List<ProcessedData> result = JsonProcessor.processJson(inputStream);

        assertEquals(0, result.size());
    }
}

