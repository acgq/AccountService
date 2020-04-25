package com.github.accountService.exception;

import org.springframework.http.HttpStatus;

/**
 * Accounting Service ResourceNotFoundException
 */
public class ResourceNotFoundException extends ServiceException {
    public ResourceNotFoundException(String message) {
        super(message);
        this.setErrorType(ErrorType.Client);
        this.setErrorCode("RESOURCE_NOT_FOUND");
        this.setHttpStatus(HttpStatus.NOT_FOUND);
    }
}
