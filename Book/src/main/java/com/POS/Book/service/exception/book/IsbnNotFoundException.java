package com.POS.Book.service.exception.book;

import lombok.Getter;

@Getter
public class IsbnNotFoundException extends RuntimeException {

    private final String message;

    public IsbnNotFoundException(String isbn) {
        message = "Could not find " + isbn + " isbn";
    }
}
