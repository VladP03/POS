package com.POS.Book.service.exception.book.NotFound;

public class GenreAndYearNotFoundException extends RuntimeException{
    public GenreAndYearNotFoundException(String message) {
        super(message);
    }
}
