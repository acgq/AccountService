package com.github.accounting.exception;

public enum BizErrorCode {
    INCORRECT_CREDENTIAL("INCORRECT_CREDENTIAL"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    INVALID_PARAMETER("INVALID_PARAMETER"),
    UN_AUTHORIZED("UN_AUTHORIZED");

    private String message;

    BizErrorCode(String message) {
        this.message = message;
    }
}
