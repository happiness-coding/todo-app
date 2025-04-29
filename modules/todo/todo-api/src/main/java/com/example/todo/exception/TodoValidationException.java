// TodoValidationException.java
package com.example.todo.exception;

public class TodoValidationException extends TodoException {
    public TodoValidationException(String message, String errorCode) {
        super(errorCode, message);
    }
}