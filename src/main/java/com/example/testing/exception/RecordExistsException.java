package com.example.testing.exception;

public class RecordExistsException extends RuntimeException{
    public RecordExistsException(String message){
        super(message);
    }

    public RecordExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
