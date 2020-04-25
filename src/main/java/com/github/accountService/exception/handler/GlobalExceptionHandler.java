package com.github.accountService.exception.handler;

import com.github.accountService.exception.ErrorResponse;
import com.github.accountService.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<?> handleResourceNotFoundException(ServiceException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(ex.getHttpStatus().value())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .errorType(ex.getErrorType())
                .build();
        return ResponseEntity.status(ex.getHttpStatus())
                .body(response);
    }
}
