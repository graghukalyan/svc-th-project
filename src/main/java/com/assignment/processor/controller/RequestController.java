package com.assignment.processor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    Logger logger = LoggerFactory.getLogger(RequestController.class);

    @GetMapping("/messages")
    public String getMessage() {
        return "Hello from Sample Project !";
    }

}
