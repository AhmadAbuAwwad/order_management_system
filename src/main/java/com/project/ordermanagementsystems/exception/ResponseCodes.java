package com.project.ordermanagementsystems.exception;

import lombok.Getter;

@Getter
public enum ResponseCodes {
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHENTICATED(401, "UNAUTHENTICATED"),
    RECAPTCHA(401, "RECAPTCHA"),
    UNAUTHORIZED(403, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT"),
    INTERNAL_SERVER_ERROR(500, "INTERVAL_SERVER_ERROR")
    ;

    private int code;
    private String type;

    ResponseCodes(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
