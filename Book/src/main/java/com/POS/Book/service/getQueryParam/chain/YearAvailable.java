package com.POS.Book.service.getQueryParam.chain;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.Book;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.exception.book.YearNotFoundException;
import com.POS.Book.service.getQueryParam.Chain;

import java.util.List;
import java.util.Optional;

public class YearAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (bookFilter.getYear() != null) {
            Optional<List<Book>> optionalBookList = Optional.ofNullable(bookRepository.findByYear(bookFilter.getYear()));

            return optionalBookList.map(books -> BookAdapter.toDTOList(books))
                    .orElseThrow(() -> new YearNotFoundException(bookFilter.getYear()));
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }
}
