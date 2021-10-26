package com.POS.Book.service.exception;

import com.POS.Book.service.exception.book.IsbnNotFoundException;
import com.POS.Book.service.exception.book.TitleNotFoundException;
import com.POS.Book.service.exception.book.YearNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error("MethodArgumentNotValidException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessage(errorMessage)
                .debugMessage("")
                .build();
    }


    @ExceptionHandler(IsbnNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleIsbnNotFoundException(IsbnNotFoundException exception) {
        String errorMessage = "Could not found this ISBN: " + exception.getMessage();
        String debugMessage = "Try another ISBN";

        log.error("IsbnNotFoundException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .build();
    }

//    @ExceptionHandler(TitleNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ApiError handleTitleNotFoundException(TitleNotFoundException exception) {
//        return ApiError.builder()
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .errorMessage(exception.getMessage())
//                .debugMessage("")
//                .build();
//    }
//
//    @ExceptionHandler(YearNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ApiError handleYearNotFoundException(YearNotFoundException exception) {
//        return ApiError.builder()
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .errorMessage(exception.getMessage())
//                .debugMessage("")
//                .build();
//    }
}
