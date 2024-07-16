package com.example.fileprocessing.controller;

import com.example.fileprocessing.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileControllerTest {

    @InjectMocks
    private FileController fileController;

    @Mock
    private FileService fileService;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleFileUpload() throws IOException {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "name,age\nJohn,30\nJane,25".getBytes());
        String outputPath = File.createTempFile("output", ".csv").getAbsolutePath();
        when(fileService.processFile(any(MultipartFile.class))).thenReturn(outputPath);

        // Call the method to test
        String viewName = fileController.handleFileUpload(file, model);

        // Verify
        verify(fileService, times(1)).processFile(any(MultipartFile.class));
        verify(model, times(1)).addAttribute("message", "File processed successfully");
        verify(model, times(1)).addAttribute("downloadLink", "/download?file=" + URLEncoder.encode(outputPath, StandardCharsets.UTF_8.toString()));
        assertEquals("result", viewName);
    }

    @Test
    public void testHandleFileUploadError() throws IOException {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "name,age\nJohn,30\nJane,25".getBytes());
        when(fileService.processFile(any(MultipartFile.class))).thenThrow(new RuntimeException("Mocked processing error"));

        // Call the method to test
        String viewName = fileController.handleFileUpload(file, model);

        // Verify
        verify(fileService, times(1)).processFile(any(MultipartFile.class));
        verify(model, times(1)).addAttribute("message", "Failed to process file: Mocked processing error");
        assertEquals("result", viewName);
    }

    @Test
    public void testHandleJsonFileUpload() throws IOException {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json", "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]".getBytes());
        String outputPath = File.createTempFile("output", ".json").getAbsolutePath();
        when(fileService.processJsonFile(any(MultipartFile.class))).thenReturn(outputPath);

        // Call the method to test
        String viewName = fileController.handleJsonFileUpload(file, model);

        // Verify
        verify(fileService, times(1)).processJsonFile(any(MultipartFile.class));
        verify(model, times(1)).addAttribute("message", "JSON file processed successfully");
        verify(model, times(1)).addAttribute("downloadLink", "/download?file=" + URLEncoder.encode(outputPath, StandardCharsets.UTF_8.toString()));
        assertEquals("result", viewName);
    }

    @Test
    public void testHandleJsonFileUploadError() throws IOException {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json", "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]".getBytes());
        when(fileService.processJsonFile(any(MultipartFile.class))).thenThrow(new RuntimeException("Mocked processing error"));

        // Call the method to test
        String viewName = fileController.handleJsonFileUpload(file, model);

        // Verify
        verify(fileService, times(1)).processJsonFile(any(MultipartFile.class));
        verify(model, times(1)).addAttribute("message", "Failed to process JSON file: Mocked processing error");
        assertEquals("result", viewName);
    }

    @Test
    public void testDownloadFile() throws IOException {
        // Prepare test data
        String filePath = File.createTempFile("output", ".csv").getAbsolutePath();
        File file = new File(filePath);
        Resource resource = new FileSystemResource(file);

        // Call the method to test
        ResponseEntity<Resource> response = fileController.downloadFile(filePath);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource, response.getBody());
        assertEquals("attachment; filename=\"" + file.getName() + "\"", response.getHeaders().getContentDisposition().toString());
    }

    @Test
    public void testDownloadFileNotFound() {
        // Prepare test data
        String filePath = "/path/to/nonexistent.csv";

        // Call the method to test
        ResponseEntity<Resource> response = fileController.downloadFile(filePath);

        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testIndex() {
        // Call the method to test
        String viewName = fileController.index();

        // Verify
        assertEquals("index", viewName);
    }
}
