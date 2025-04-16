package com.example.todo.exception;

public class InvalidFileNameException extends Exception {
    public InvalidFileNameException(String message) {
        super(message);
    }
}