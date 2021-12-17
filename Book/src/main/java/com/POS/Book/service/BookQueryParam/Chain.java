package com.POS.Book.service.BookQueryParam;

import com.POS.Book.model.Book;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;

import java.util.List;

public interface Chain {

    void setNextChain(Chain nextInChain);

    List<Book> run(BookFilter bookFilter, BookRepository bookRepository);
}
