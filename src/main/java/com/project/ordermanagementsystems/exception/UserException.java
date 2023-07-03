package com.project.ordermanagementsystems.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserException extends RuntimeException {

    private String logMessage;

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String message, String logMessage) {
        super(message);
        setLogMessage(logMessage);
    }

    public UserException(String message) {
        super(message);
        setLogMessage(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
