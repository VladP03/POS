package com.POS.Book.service.exception.token;

public class EmptyTokenException extends RuntimeException {

    public EmptyTokenException(String message) {
        super(message);
    }
}
