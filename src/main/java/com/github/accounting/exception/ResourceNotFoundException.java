package com.github.accounting.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ServiceException {
    /**
     *  Constructor for ResourceNotFoundException.
     * @param message exception message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.setErrorType(ErrorType.Client);
        this.setErrorCode("RESOURCE_NOT_FOUND");
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
