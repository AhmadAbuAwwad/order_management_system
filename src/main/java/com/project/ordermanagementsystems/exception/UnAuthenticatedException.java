package com.project.ordermanagementsystems.exception;

import lombok.Data;

@Data
public class UnAuthenticatedException extends UserException {

    private ResponseCodes responseCodes;

    public UnAuthenticatedException(String message, ResponseCodes responseCodes) {
        super(message);
        this.responseCodes = responseCodes;
    }

    public UnAuthenticatedException(String message, ResponseCodes responseCodes, Throwable cause) {
        super(message, cause);
        this.responseCodes = responseCodes;
    }
}
