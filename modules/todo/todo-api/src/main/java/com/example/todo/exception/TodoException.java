package com.example.todo.exception;

/**
 * Base exception class that includes error code functionality
 */
public abstract class TodoException extends Exception {
    private final String errorCode;

    public TodoException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TodoException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getLanguageKey() {
        return TodoErrorCodes.getLanguageKey(errorCode);
    }
}