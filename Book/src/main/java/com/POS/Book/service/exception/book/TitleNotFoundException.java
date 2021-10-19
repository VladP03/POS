package com.POS.Book.service.exception.book;

import lombok.Getter;

@Getter
public class TitleNotFoundException extends RuntimeException{

    private final String message;

    public TitleNotFoundException(String title) {
        message = "Could not find " + title + " title";
    }
}
