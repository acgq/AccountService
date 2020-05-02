package com.github.accounting.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;
    private ErrorType errorType;

    public enum ErrorType {
        Client,
        Server,
        Unknown
    }

    public ServiceException(String message) {
        super(message);
    }
}
