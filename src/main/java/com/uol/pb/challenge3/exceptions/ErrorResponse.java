package com.uol.pb.challenge3.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String error;
    private List<String> messages;
    private Instant instant;

    public ErrorResponse(int status, String error, List<String> messageList) {
        this.status = status;
        this.error = error;
        this.messages = messageList;
        this.instant = Instant.now();
    }


}
