package com.github.accounting.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {
    /**
     * Constructor .
     * @param message exception message.
     */
    public InvalidParameterException(String message) {
        super(message);
        this.setErrorCode(BizErrorCode.INVALID_PARAMETER);
        this.setErrorType(ErrorType.Client);
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
