package com.assignment.processor.controller;

import com.assignment.processor.contract.DataServerResponse;
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

    Logger logger = LoggerFactory.getLogger(RequestController.class);

    @GetMapping("/messages")
    public String getMessage() {
        return "Hello from Sample Project !";
    }

    @GetMapping("/test")
    public DataServerResponse getLogMessage() {
        return requestServiceHandler.parseCommands(new String[] {"START",
                "GET most_popular_leader",
                "PUT georgew {\"first_name\":\"George\", \"last_name\":\"Washington\", \"role\":\"President\"}",
                "COMMIT"});
    }

}
