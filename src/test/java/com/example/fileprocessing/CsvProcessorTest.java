package com.example.fileprocessing;

import com.example.fileprocessing.util.CsvProcessor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CsvProcessorTest {

    @Test
    public void testProcessCsv() throws IOException {
        File inputFile = new File("test_input.csv");
        FileWriter writer = new FileWriter(inputFile);
        writer.write("hello\nworld");
        writer.close();

        File outputFile = new File("test_output.csv");
        CsvProcessor.processCsv(inputFile, outputFile);

        assertTrue(outputFile.exists());
        // Additional assertions can be made to check the content of the output file.
    }
}
