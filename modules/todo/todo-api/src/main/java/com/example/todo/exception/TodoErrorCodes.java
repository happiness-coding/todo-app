package com.example.todo.exception;

/**
 * Centralized repository of error codes for the Todo application.
 * Each code has a corresponding message key in Language.properties.
 */
public class TodoErrorCodes {
    // File-related error codes
    public static final String FILE_SIZE_EXCEEDED = "FILE_001";
    public static final String FILE_NAME_FORMAT_INVALID = "FILE_002";
    public static final String FILE_NAME_INVALID = "FILE_003";
    public static final String INVALID_PROCESSING_PERIOD = "FILE_004";
    public static final String SERVICE_UNAVAILABLE = "SVC_001";

    // Other error codes for future use
    public static final String GENERAL_ERROR = "GEN_001";

    /**
     * Gets the language key for an error code.
     * @param errorCode The error code
     * @return The corresponding language key
     */
    public static String getLanguageKey(String errorCode) {
        return "error-" + errorCode.toLowerCase();
    }
}