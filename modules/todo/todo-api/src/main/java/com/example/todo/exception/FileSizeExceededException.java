package com.example.todo.exception;

public class FileSizeExceededException extends Exception {
    public FileSizeExceededException(String message) {
        super(message);
    }
}