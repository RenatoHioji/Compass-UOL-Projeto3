package com.uol.pb.challenge3.jms;

import org.springframework.util.ErrorHandler;

public class CustomErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        System.err.println("An error occurred while processing JMS message: " + t.getMessage());
    }
}
