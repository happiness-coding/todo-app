package com.example.todo.exception;

public class ServiceUnavailableException extends TodoException {
    public ServiceUnavailableException() {
        super(TodoErrorCodes.SERVICE_UNAVAILABLE, "Backend service unavailable");
    }

    public ServiceUnavailableException(String message) {
        super(TodoErrorCodes.SERVICE_UNAVAILABLE, message);
    }
}