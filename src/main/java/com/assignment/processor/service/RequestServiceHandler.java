package com.assignment.processor.service;

import com.assignment.processor.contract.DataServerResponse;
import com.assignment.processor.service.helper.DataModificationHelper;
import com.assignment.processor.service.helper.TransactionControlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Optional;

@ComponentScan(basePackages = "com.assignment.processor")
@Component
public class RequestServiceHandler {
    @Autowired
    private DataModificationHelper dataModificationHelper;
    @Autowired
    private TransactionControlHelper transactionControlHelper;
    private final static Logger logger = LoggerFactory.getLogger(RequestServiceHandler.class);

    public Optional<DataServerResponse> parseCommand(String dataServerRequest) {

        logger.info (String.format("Received Input command : %s", dataServerRequest));

        if (dataServerRequest != null) {

            if (dataServerRequest.equalsIgnoreCase("START") ||
                    dataServerRequest.equalsIgnoreCase("COMMIT") ||
                    dataServerRequest.equalsIgnoreCase("ROLLBACK")) {
                logger.info ("Received transaction command & proceeding to process command");
                parseTransactionCommand(dataServerRequest);
            } else {
                return Optional.of(generateResponse(parseDataModificationCommand(dataServerRequest)));
            }
        }
        return Optional.empty();
    }

    private DataServerResponse generateResponse(String responseMessage) {

        if ("NOT_FOUND".equals(responseMessage)) {
            return DataServerResponse.builder().status("ERROR").
                    message("Unable to find requested value")
                    .build();
        }
        //Handling GET response as a special case
        else if (!("OK".equals(responseMessage)) && !("ERROR".equals(responseMessage))) {
            return DataServerResponse.builder().status("OK").
                    result(responseMessage)
                    .build();
        } else if ("ERROR".equals(responseMessage)) {
            return DataServerResponse.builder().status("ERROR").
                    message("Unexpected issue encountered")
                    .build();
        }
        return DataServerResponse.builder().status("OK")
                .build();
    }

    private String parseDataModificationCommand(String request) {

        String[] modificationCommand = request.split("\\s+",2);

        return switch (modificationCommand[0]) {

            case "PUT" -> {
                logger.info("Processing PUT request");
                String[] putKVPair = modificationCommand[1].split("\\s+", 2);
                yield dataModificationHelper.processPutRequest(putKVPair[0], putKVPair[1]);
            }

            case "GET" -> {

                logger.info("Processing GET request");
                yield DataModificationHelper.processGetRequest(modificationCommand[1]);
            }

            case "DEL" -> {

                logger.info("Processing DEL request");
                yield dataModificationHelper.processDeleteRequest(modificationCommand[1]);
            }

            default -> throw new IllegalArgumentException("Unexpected DataModification Command: " + modificationCommand[0]);
        };

    }

    private void parseTransactionCommand(String transactionCommand) {

        switch (transactionCommand) {

            case "COMMIT":
                logger.info("Processing COMMIT");
                transactionControlHelper.processTransactionCommit();
                break;

            case "ROLLBACK":
                logger.info("Processing Rollback");
                transactionControlHelper.processTransactionRollback();
                break;

            case "START":
                logger.info("Processing START");
                transactionControlHelper.processTransactionStart();
                break;

            default:
                throw new IllegalArgumentException("Unexpected TransactionControl Command: " + transactionCommand);
        }

    }
}