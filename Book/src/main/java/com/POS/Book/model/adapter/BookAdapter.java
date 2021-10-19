package com.POS.Book.model.adapter;

import com.POS.Book.model.BookDTO;
import com.POS.Book.repository.book.Book;

import java.util.ArrayList;
import java.util.List;

public final class BookAdapter {

    private BookAdapter() {
    }

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .year(book.getYear())
                .gender(book.getGender())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }

    public static Book fromDTO(BookDTO bookDTO) {
        return Book.builder()
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .year(bookDTO.getYear())
                .gender(bookDTO.getGender())
                .price(bookDTO.getPrice())
                .stock(bookDTO.getStock())
                .build();
    }

    public static List<BookDTO> toDTOList(List<Book> bookList) {
        List<BookDTO> bookDTOList = new ArrayList<>();

        for (Book book : bookList) {
            bookDTOList.add(toDTO(book));
        }

        return bookDTOList;
    }

    public static List<Book> fromDTOList(List<BookDTO> bookDTOList) {
        List<Book> bookList = new ArrayList<>();

        for (BookDTO bookDTO : bookDTOList) {
            bookList.add(fromDTO(bookDTO));
        }

        return bookList;
    }
}
