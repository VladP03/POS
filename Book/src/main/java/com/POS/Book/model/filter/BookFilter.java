package com.POS.Book.model.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public final class BookFilter {

    private final String isbn;
    private final String title;
    private final String publisher;
    private final Integer year;
    private final Double price;
}
