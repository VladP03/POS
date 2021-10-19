package com.POS.Book.service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
public class ApiError implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy - hh:mm:ss")
    private final LocalDateTime timestamp;

    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;

    public ApiError(HttpStatus httpStatus, String message, String debugMessage) {
        this.timestamp = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
