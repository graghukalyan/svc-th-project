package com.assignment.processor.contract;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Builder
@Getter
@Setter
public class DataServerResponse {

    String status;

    Optional<String> result;

    Optional<String> message;

}
