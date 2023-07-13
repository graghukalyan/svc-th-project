package com.assignment.processor.service;

import com.assignment.processor.contract.DataServerResponse;
import com.assignment.processor.util.ProcessorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceHandler {

    Logger logger = LoggerFactory.getLogger(RequestServiceHandler.class);

//    public DataServerResponse parseCommands(DataServerRequest dataServerRequest) {
//
//        return  null;
//
//    }

    public DataServerResponse parseCommands(String[] dataServerRequest) {

        System.out.println("Start Inputs:");

        for (String request : dataServerRequest) {
            System.out.println("Server Request " + request);

            //TO-DO : Clear this step
            if (request.equals("START")) {
                continue;
            }
            parseDataModificationCommand(request);

        }
        return DataServerResponse.builder().build();
    }

    private void parseDataModificationCommand(String request) {

        String[] modificationCommand = request.split("\\s+",2);

        switch (modificationCommand[0]) {

            case "PUT":
                System.out.println("Processing PUT request");
                String[] putKVPair = modificationCommand[1].split("\\s+",2);

                ProcessorUtil.processPutRequest(putKVPair[0], putKVPair[1]);
                break;

            case "GET":
                System.out.println("Processing GET request");
                ProcessorUtil.processGetRequest(modificationCommand[1]);
                break;

            case "DEL":
                System.out.println("Processing DEL request");
                ProcessorUtil.processDeleteRequest(modificationCommand[1]);
                break;

            default:
                throw new IllegalArgumentException("Unexpected DataModification Command: " + modificationCommand[0]);
        }

    }


}