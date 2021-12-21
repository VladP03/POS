package com.facultate.POS.service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy - hh:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private HttpStatus httpStatus;
    private String errorMessage;
    private String debugMessage;
}
