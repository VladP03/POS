package com.POS.Book.model.adapter;

import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.repository.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookAdapter {

    public BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .publisher(book.getPublisher())
                .year(book.getYear())
                .genre(book.getGenre())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }

    public Book fromDTO(BookDTO bookDTO) {
        return Book.builder()
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .publisher(bookDTO.getPublisher())
                .year(bookDTO.getYear())
                .genre(bookDTO.getGenre())
                .price(bookDTO.getPrice())
                .stock(bookDTO.getStock())
                .build();
    }

    public List<BookDTO> toDTOList(List<Book> bookList) {
        return bookList.stream().map(BookAdapter::toDTO).collect(Collectors.toList());
    }

    public List<Book> fromDTOList(List<BookDTO> bookDTOList) {
        return bookDTOList.stream().map(BookAdapter::fromDTO).collect(Collectors.toList());
    }
}
