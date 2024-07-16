package com.example.fileprocessing.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProcessedDataTest {

    @Test
    void testProcessedData() {
        // Correct constructor with both name and age
        ProcessedData data = new ProcessedData("John", 30);

        assertEquals("John", data.getName());
        assertEquals(30, data.getAge());

        data.setName("Jane");
        data.setAge(25);

        assertEquals("Jane", data.getName());
        assertEquals(25, data.getAge());
    }
}
