package com.POS.Book.model.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class BookFilter {

    private final Integer page;
    private final Integer items_per_page;

    private final String title;
    private final String publisher;
    private final Integer year;
    private final Double price;
}
