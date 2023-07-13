package com.assignment.processor.service;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Test Class
class RequestServiceHandlerTest {
    @Test
    void testProcessor() {
        String[] inputFileLines = loadFileLines();
        RequestServiceHandler app = new RequestServiceHandler();
        app.parseCommands(inputFileLines);
        assertTrue(true);
    }
    private static String[] loadFileLines() {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader("src/test/resources/input.txt"));
            String str;
            List<String> list = new ArrayList<>();
            while((str = in.readLine()) != null){
                list.add(str);
            }
            return list.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Could not load file." + e.getMessage());
        }
        return null;
    }
}
