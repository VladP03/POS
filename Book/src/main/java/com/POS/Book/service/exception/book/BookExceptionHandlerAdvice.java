package com.POS.Book.service.exception.book;

import com.POS.Book.service.exception.ApiError;
import com.POS.Book.service.exception.book.NotFound.BookNotFoundException;
import com.POS.Book.service.exception.book.NotFound.GenreAndYearNotFoundException;
import com.POS.Book.service.exception.book.NotFound.GenreNotFoundException;
import com.POS.Book.service.exception.book.NotFound.IsbnNotFoundException;
import com.POS.Book.service.exception.book.NotFound.YearNotFoundException;
import com.POS.Book.service.exception.book.unique.TitleUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class BookExceptionHandlerAdvice {

    @ExceptionHandler(TitleUniqueException.class)
    public ResponseEntity<ApiError> handleTitleUniqueException(TitleUniqueException exception) {
        String errorMessage = "Title: " + exception.getMessage() + " already exists";
        String debugMessage = "Try a different title";

        log.error("TitleUniqueException has been thrown with the following message: " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }

    @ExceptionHandler(IsbnNotFoundException.class)
    public ResponseEntity<ApiError> handleIsbnNotFoundException(IsbnNotFoundException exception) {
        String errorMessage = "Could not found this ISBN: " + exception.getMessage();
        String debugMessage = "Try another ISBN";

        log.error("IsbnNotFoundException has been thrown with the following message: " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }

    @ExceptionHandler(YearNotFoundException.class)
    public ResponseEntity<ApiError> handleYearNotFoundException(YearNotFoundException exception) {
        String errorMessage = "Could not found any book published in: " + exception.getMessage();
        String debugMessage = "Try a different year";

        log.error("YearNotFoundException has been thrown with the following message: " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ApiError> handleGenreNotFoundException(GenreNotFoundException exception) {
        String errorMessage = "Could not found any book with genre: " + exception.getMessage();
        String debugMessage = "Try a different genre";

        log.error("GenreNotFoundException has been thrown with the following message: " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException exception) {
        String errorMessage = "Could not found any book with following filters: " + exception.getMessage();
        String debugMessage = "Try different filters";

        log.error("BookNotFoundException has been thrown with the following message: " + errorMessage);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage(errorMessage)
                        .debugMessage(debugMessage)
                        .build()
                );
    }

    @ExceptionHandler(GenreAndYearNotFoundException.class)
    public ResponseEntity<ApiError> handleGenreAndYearNotFoundException(GenreAndYearNotFoundException exception) {
        String errorMessage = "Could not found any book with following filters: " + exception.getMessage();
        String debugMessage = "Try different filters";

        log.error("GenreAndYearNotFoundException has been thrown with the following message: " + errorMessage);

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
