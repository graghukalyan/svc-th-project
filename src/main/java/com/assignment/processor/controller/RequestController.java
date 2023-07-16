package com.assignment.processor.controller;

import com.assignment.processor.service.RequestServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @Autowired
    RequestServiceHandler requestServiceHandler;

    private final static Logger logger = LoggerFactory.getLogger(RequestController.class);

    @GetMapping("/messages")
    public String getMessage() {
        return "Hello from Take Home Project !";
    }
}
