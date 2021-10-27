package com.POS.Book.service.exception.book.unique;

public class TitleUniqueException extends RuntimeException {
    public TitleUniqueException(String message) {
        super(message);
    }
}
