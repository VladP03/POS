package com.POS.Book.service.getQueryParam.chain;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.Book;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.exception.book.IsbnNotFoundException;
import com.POS.Book.service.getQueryParam.Chain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IsbnAvailable implements Chain {

    private Chain nexChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        this.nexChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (bookFilter.getIsbn() != null) {
            Book book = Optional.ofNullable(bookRepository.findByIsbn(bookFilter.getIsbn()))
                    .orElseThrow(() -> new IsbnNotFoundException(bookFilter.getIsbn()));

            return BookAdapter.toDTOList(Collections.singletonList(book));
        } else {
            return nexChain.run(bookFilter, bookRepository);
        }
    }
}
