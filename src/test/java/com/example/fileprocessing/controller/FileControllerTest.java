package com.example.fileprocessing.controller;

import com.example.fileprocessing.service.FileService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    void testFileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk());
    }
}
