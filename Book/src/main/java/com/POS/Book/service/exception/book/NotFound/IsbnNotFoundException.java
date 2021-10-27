package com.POS.Book.service.exception.book.NotFound;

import lombok.Getter;

@Getter
public class IsbnNotFoundException extends RuntimeException {

    public IsbnNotFoundException(String message) {
        super(message);
    }
}
