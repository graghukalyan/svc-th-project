package com.assignment.processor.service;

import com.assignment.processor.contract.DataServerResponse;
import com.assignment.processor.service.helper.DataModificationHelper;
import com.assignment.processor.service.helper.TransactionControlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceHandler {

    private final static Logger logger = LoggerFactory.getLogger(RequestServiceHandler.class);

//    public DataServerResponse parseCommands(DataServerRequest dataServerRequest) {
//
//        return  null;
//
//    }

    public DataServerResponse parseCommand(String dataServerRequest) {

        logger.info (String.format("Received Input command : %s", dataServerRequest));

        if (dataServerRequest != null) {

            //TO-DO : Clear this step
            if (dataServerRequest.equalsIgnoreCase("START") ||
                    dataServerRequest.equalsIgnoreCase("COMMIT") ||
                    dataServerRequest.equalsIgnoreCase("ROLLBACK")) {
                logger.info ("Received transaction command & proceeding to process command");
                parseTransactionCommand(dataServerRequest);
            } else {
                parseDataModificationCommand(dataServerRequest);
            }
        }
        return DataServerResponse.builder().build();
    }

    private void parseDataModificationCommand(String request) {

        String[] modificationCommand = request.split("\\s+",2);

        switch (modificationCommand[0]) {

            case "PUT":
                logger.info ("Processing PUT request");
                String[] putKVPair = modificationCommand[1].split("\\s+",2);

                DataModificationHelper.processPutRequest(putKVPair[0], putKVPair[1]);
                break;

            case "GET":
                logger.info("Processing GET request");
                DataModificationHelper.processGetRequest(modificationCommand[1]);
                break;

            case "DEL":
                logger.info("Processing DEL request");
                DataModificationHelper.processDeleteRequest(modificationCommand[1]);
                break;

            default:
                throw new IllegalArgumentException("Unexpected DataModification Command: " + modificationCommand[0]);
        }

    }

    private void parseTransactionCommand(String transactionCommand) {

        switch (transactionCommand) {

            case "COMMIT":
                logger.info("Processing COMMIT");
                TransactionControlHelper.processTransactionCommit();
                break;

            case "ROLLBACK":
                logger.info("Processing Rollback");
                TransactionControlHelper.processTransactionRollback();
                break;

            case "START":
                logger.info("Processing START");
                TransactionControlHelper.processTransactionStart();
                break;

            default:
                throw new IllegalArgumentException("Unexpected TransactionControl Command: " + transactionCommand);
        }

    }
}