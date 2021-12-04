package com.POS.Book.service.exception.author;

import com.POS.Book.service.exception.ApiError;
import com.POS.Book.service.exception.author.NotFound.AuthorNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class AuthorExceptionHandlerAdvice {

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleAuthorNotFoundException(AuthorNotFoundException exception) {
        String errorMessage = "Could not found an author with id " + exception.getMessage();
        String debugMessage = "";

        log.error("AuthorNotFoundException has been thrown with the following message: " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }
}
