package com.example.todo.exception;

public class InvalidFileNameException extends TodoException {
    public InvalidFileNameException() {
        super(TodoErrorCodes.FILE_NAME_INVALID, "Invalid file name");
    }

    public InvalidFileNameException(String message) {
        super(TodoErrorCodes.FILE_NAME_INVALID, message);
    }
}