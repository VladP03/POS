package com.POS.Book.service.exception.book;

import lombok.Getter;

@Getter
public class YearNotFoundException extends RuntimeException {

    private final String message;

    public YearNotFoundException(Integer year) {
        message = "Could not find " + year + " year";
    }
}
