package com.github.accountService.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {

    public InvalidParameterException(String message) {
        super(message);
        this.setErrorCode("INVALID_PARAMETER");
        this.setErrorType(ErrorType.Client);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
