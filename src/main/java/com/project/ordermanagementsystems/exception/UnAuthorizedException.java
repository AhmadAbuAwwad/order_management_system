package com.project.ordermanagementsystems.exception;

public class UnAuthorizedException extends UserException {
    public UnAuthorizedException(String message, ResponseCodes responseCodes) {
        super(message);
    }
}
