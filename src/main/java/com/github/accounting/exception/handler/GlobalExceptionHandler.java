package com.github.accounting.exception.handler;

import com.github.accounting.exception.BizErrorCode;
import com.github.accounting.exception.ErrorResponse;
import com.github.accounting.exception.ServiceException;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(IncorrectCredentialsException.class)
    ResponseEntity<?> handleIncorrectCredentialsException(IncorrectCredentialsException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errorCode(BizErrorCode.INCORRECT_CREDENTIAL)
                .errorType(ServiceException.ErrorType.Client)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(response);
    }
}
