package com.facultate.POS.Book.model.adapter;

import com.facultate.POS.Book.model.BookDTO;
import com.facultate.POS.Book.repository.book.Book;

public final class BookAdapter {

    private BookAdapter() {}

    public static BookDTO toBookDTO (Book book) {
        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .year(book.getYear())
                .gender(book.getGender())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }

    public static Book fromBookDTO (BookDTO bookDTO) {
        return Book.builder()
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .year(bookDTO.getYear())
                .gender(bookDTO.getGender())
                .price(bookDTO.getPrice())
                .stock(bookDTO.getStock())
                .build();
    }
}
