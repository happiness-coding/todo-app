package com.example.todo.exception;

public class FileSizeExceededException extends TodoException {
    public FileSizeExceededException() {
        super(TodoErrorCodes.FILE_SIZE_EXCEEDED, "File size exceeded the maximum limit");
    }

    public FileSizeExceededException(String message) {
        super(TodoErrorCodes.FILE_SIZE_EXCEEDED, message);
    }
}