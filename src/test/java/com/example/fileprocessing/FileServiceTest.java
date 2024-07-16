package com.example.fileprocessing;

import com.example.fileprocessing.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    public void setUp() {
        fileService = Mockito.mock(FileService.class);
    }

    @Test
    public void testProcessFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.csv", "Hello, World!".getBytes());
        when(fileService.processFile(multipartFile)).thenReturn("output_test.csv");

        String result = fileService.processFile(multipartFile);
        assertEquals("output_test.csv", result);

        verify(fileService, times(1)).processFile(multipartFile);
    }
}
