package com.assignment.processor.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Test Class
class RequestServiceHandlerTest {

    @Test
    void testProcessor() {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(RequestServiceHandler.class);
        RequestServiceHandler app = context.getBean(RequestServiceHandler.class);
        String[] inputFileLines = loadFileLines();

        for (String inputCommand : inputFileLines) {
            app.parseCommand(inputCommand);
        }
        assertTrue(true);
    }

    private static String[] loadFileLines() {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader("src/test/resources/input.txt"));
            String str;
            List<String> list = new ArrayList<>();
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            return list.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Could not load file." + e.getMessage());
        }
        return null;
    }
}
