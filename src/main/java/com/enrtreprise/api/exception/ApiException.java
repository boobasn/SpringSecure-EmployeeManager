package com.enrtreprise.api.exception;

import java.time.LocalDateTime;
import lombok.Data;
@Data
public class ApiException {
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ApiException(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // getters / setters
}
