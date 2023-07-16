package com.assignment.processor.contract;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DataServerRequest {

    private Transaction transactionType;
    private Command dataModificationCommand;
    public enum Transaction {
        START,
        COMMIT,
        ROLLBACK
    }

    public enum Command {
        PUT,
        GET,
        DEL
    }


}
