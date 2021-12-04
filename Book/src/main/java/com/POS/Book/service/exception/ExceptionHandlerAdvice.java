package com.POS.Book.service.exception;

import com.POS.Book.service.exception.author.AuthorNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String debugMessage;

        return ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessage(errorMessage)
                .debugMessage("")
                .build();
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseBody
    public ApiError handleAuthorNotFoundException(AuthorNotFoundException exception) {
        String errorMessage = "Could not found an author " + exception.getMessage();
        String debugMessage = "";

        log.error("AuthorNotFoundException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .build();
    }
}
