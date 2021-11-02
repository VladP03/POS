package com.POS.Book.service.BookQueryParam;

import com.POS.Book.model.filter.BookFilter;

public class ChainOfResponsability {

    public Chain decider(BookFilter bookFilter) {
        if (bookFilter.getPage() != null || bookFilter.getItems_per_page() != null) {
            return new COPPageDisplay().getFirstChain();
        } else if (bookFilter.getYear() != null || bookFilter.getGenre() != null){
            return new COPGenreYear().getFirstChain();
        } else {
            // TODO: Create custom exception in case none of the query param are available
            throw new RuntimeException("TODO");
        }
    }
}

