package com.github.accountService.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int statusCode;
    private String errorCode;
    private ServiceException.ErrorType errorType;
    private String message;

}
