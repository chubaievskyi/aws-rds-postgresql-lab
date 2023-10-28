package com.chubaievskyi.exceptions;

public class DatabaseExecutionException extends RuntimeException {

    public DatabaseExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

