package com.POS.Book.service.exception.book.NotFound;

import lombok.Getter;

@Getter
public class YearNotFoundException extends RuntimeException {
    public YearNotFoundException(String message) {
        super(message);
    }
}
