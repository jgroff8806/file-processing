package com.example.fileprocessing.service;

import com.example.fileprocessing.model.ProcessedData;
import com.example.fileprocessing.util.CsvProcessor;
import com.example.fileprocessing.util.JsonProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CsvProcessor.class, JsonProcessor.class})
public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private MultipartFile multipartFile;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessFile() throws IOException {
        // Prepare test data
        when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        when(multipartFile.getBytes()).thenReturn("name,age\nJohn,30\nJane,25".getBytes());

        // Mock the static method
        PowerMockito.mockStatic(CsvProcessor.class);
        File outputFile = File.createTempFile("output", ".csv");
        when(CsvProcessor.processCsv(any(File.class), any(File.class)))
                .thenAnswer(invocation -> {
                    File outFile = invocation.getArgument(1);
                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        fos.write("name,age\nJohn,30\nJane,25".getBytes());
                    }
                    return Collections.singletonList(new ProcessedData("John", 30));
                });

        // Call the method to test
        String outputPath = fileService.processFile(multipartFile);

        // Verify
        assertNotNull(outputPath);
        assertTrue(new File(outputPath).exists());
        assertEquals(outputFile.getAbsolutePath(), outputPath);

        PowerMockito.verifyStatic(CsvProcessor.class, times(1));
        CsvProcessor.processCsv(any(File.class), any(File.class));
    }

    @Test
    public void testProcessFileError() throws IOException {
        // Prepare test data
        when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        when(multipartFile.getBytes()).thenThrow(new IOException("Mocked IO Exception"));

        // Call the method to test and expect an exception
        try {
            fileService.processFile(multipartFile);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Error processing file", e.getMessage());
        }
    }

    @Test
    public void testProcessJsonFile() throws IOException {
        // Prepare test data
        when(multipartFile.getOriginalFilename()).thenReturn("test.json");
        when(multipartFile.getBytes()).thenReturn("[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]".getBytes());

        // Mock the static method
        PowerMockito.mockStatic(JsonProcessor.class);
        when(JsonProcessor.processJson(any(File.class))).thenReturn(Collections.singletonList(new ProcessedData("John", 30)));

        // Call the method to test
        String outputPath = fileService.processJsonFile(multipartFile);

        // Verify
        assertNotNull(outputPath);
        assertTrue(new File(outputPath).exists());

        PowerMockito.verifyStatic(JsonProcessor.class, times(1));
        JsonProcessor.processJson(any(File.class));
    }

    @Test
    public void testProcessJsonFileError() throws IOException {
        // Prepare test data
        when(multipartFile.getOriginalFilename()).thenReturn("test.json");
        when(multipartFile.getBytes()).thenThrow(new IOException("Mocked IO Exception"));

        // Call the method to test and expect an exception
        try {
            fileService.processJsonFile(multipartFile);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Error processing JSON file", e.getMessage());
        }
    }

    @Test
    public void testConvertMultipartFileToFile() throws IOException {
        // Prepare test data
        when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        when(multipartFile.getBytes()).thenReturn("name,age\nJohn,30\nJane,25".getBytes());

        // Call the method to test
        File file = fileService.convertMultipartFileToFile(multipartFile);

        // Verify
        assertNotNull(file);
        assertTrue(file.exists());
        assertEquals("test.csv", file.getName());
    }

    @Test(expected = IOException.class)
    public void testConvertMultipartFileToFileError() throws IOException {
        // Prepare test data
        when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        when(multipartFile.getBytes()).thenThrow(new IOException("Mocked IO Exception"));

        // Call the method to test and expect an exception
        fileService.convertMultipartFileToFile(multipartFile);
    }
}
