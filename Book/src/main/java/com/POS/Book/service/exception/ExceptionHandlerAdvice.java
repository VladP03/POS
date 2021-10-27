package com.POS.Book.service.exception;

import com.POS.Book.service.exception.author.AuthorNotFoundException;
import com.POS.Book.service.exception.book.NotFound.IsbnNotFoundException;
import com.POS.Book.service.exception.book.unique.TitleUniqueException;
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
        String debugMessage;

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

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleAuthorNotFoundException(AuthorNotFoundException exception) {
        String errorMessage = "Could not found an author with id: " + exception.getMessage();
        String debugMessage = "Try another id";

        log.error("AuthorNotFoundException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .build();
    }

    @ExceptionHandler(TitleUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleTitleUniqueException(TitleUniqueException exception) {
        String errorMessage = "Title: " + exception.getMessage() + " already exists";
        String debugMessage = "Try a different title";

        log.error("TitleUniqueException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
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
