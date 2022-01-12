package com.POS.Book.service.exception;

import com.POS.Book.interceptor.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String debugMessage;

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage(errorMessage)
                        .debugMessage("")
                        .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations().stream().findFirst().get().getMessage();
        String debugMessage;

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage(errorMessage)
                        .debugMessage("")
                        .build()
                );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException exception) {
        String errorMessage = exception.getMessage();
        String debugMessage;

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .errorMessage(errorMessage)
                        .debugMessage("")
                        .build()
                );
    }
}
