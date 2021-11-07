package com.POS.Book.service.exception.book;

import com.POS.Book.service.exception.ApiError;
import com.POS.Book.service.exception.author.AuthorNotFoundException;
import com.POS.Book.service.exception.book.NotFound.GenreNotFoundException;
import com.POS.Book.service.exception.book.NotFound.IsbnNotFoundException;
import com.POS.Book.service.exception.book.NotFound.YearNotFoundException;
import com.POS.Book.service.exception.book.unique.TitleUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class BookExceptionHandlerAdvice {

    @ExceptionHandler(TitleUniqueException.class)
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

    @ExceptionHandler(IsbnNotFoundException.class)
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

    @ExceptionHandler(YearNotFoundException.class)
    @ResponseBody
    public ApiError handleYearNotFoundException(YearNotFoundException exception) {
        String errorMessage = "Could not found any book published in: " + exception.getMessage();
        String debugMessage = "Try a different year";

        log.error("YearNotFoundException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .build();
    }

    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseBody
    public ApiError handleGenreNotFoundException(GenreNotFoundException exception) {
        String errorMessage = "Could not found any book with genre: " + exception.getMessage();
        String debugMessage = "Try a different genre";

        log.error("GenreNotFoundException has been thrown with the following message: " + errorMessage);

        return ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(errorMessage)
                .debugMessage(debugMessage)
                .build();
    }
}
