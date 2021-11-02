package com.POS.Book.model.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public final class BookFilter {

    private final Integer page;
    private final Integer items_per_page;

    private final String genre;
    private final Integer year;
}
