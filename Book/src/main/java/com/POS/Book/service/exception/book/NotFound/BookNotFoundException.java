package com.POS.Book.service.exception.book.NotFound;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
