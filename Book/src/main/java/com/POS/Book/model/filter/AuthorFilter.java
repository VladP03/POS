package com.POS.Book.model.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public final class AuthorFilter {

    private final String name;
    private final Boolean match;
}
