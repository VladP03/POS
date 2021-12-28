package com.facultate.POS.service.exception;

import com.facultate.POS.service.exception.NotFound.CollectionNotFoundException;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

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

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleHttpClientErrorException(HttpClientErrorException exception) {
        JSONObject jsonObject = new JSONObject(exception.getResponseBodyAsString());

        String errorMessage = jsonObject.getString("errorMessage");
        String debugMessage = jsonObject.getString("debugMessage");

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(ApiError.builder()
                        .httpStatus(exception.getStatusCode())
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }

    @ExceptionHandler(CollectionNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleCollectionNotFoundException(CollectionNotFoundException exception) {
        String errorMessage = "Could not found any collection for clientId: " + exception.getMessage();
        String debugMessage;

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(errorMessage)
                        .debugMessage("")
                        .build()
                );
    }
}
