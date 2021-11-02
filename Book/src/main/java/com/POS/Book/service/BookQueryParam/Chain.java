package com.POS.Book.service.BookQueryParam;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;

import java.util.List;

public interface Chain {
    void setNextChain(Chain nextInChain);

    List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository);
}
