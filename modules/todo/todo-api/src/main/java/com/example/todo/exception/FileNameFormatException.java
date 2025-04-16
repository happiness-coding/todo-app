package com.example.todo.exception;

public class FileNameFormatException extends TodoException {
    public FileNameFormatException() {
        super(TodoErrorCodes.FILE_NAME_FORMAT_INVALID, "Invalid file name format");
    }

    public FileNameFormatException(String message) {
        super(TodoErrorCodes.FILE_NAME_FORMAT_INVALID, message);
    }
}