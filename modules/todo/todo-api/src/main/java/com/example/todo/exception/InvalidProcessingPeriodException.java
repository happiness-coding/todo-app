package com.example.todo.exception;

public class InvalidProcessingPeriodException extends TodoException {
    public InvalidProcessingPeriodException() {
        super(TodoErrorCodes.INVALID_PROCESSING_PERIOD, "Invalid processing period");
    }

    public InvalidProcessingPeriodException(String message) {
        super(TodoErrorCodes.INVALID_PROCESSING_PERIOD, message);
    }
}