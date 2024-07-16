package com.example.fileprocessing.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileProcessingExceptionTest {

    @Test
    void testExceptionThrown() {
        Exception exception = assertThrows(FileProcessingException.class, () -> {
            throw new FileProcessingException("Error message");
        });

        assertEquals("Error message", exception.getMessage());
    }
}

