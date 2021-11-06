package com.POS.Book.service.BookQueryParam.chainGenreYear;

import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.Book;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.exception.book.YearNotFoundException;
import com.POS.Book.service.BookQueryParam.Chain;

import java.util.List;

public class YearAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (bookFilter.getYear() != null) {
            List<Book> bookList = bookRepository.findByYear(bookFilter.getYear());

            if (!bookList.isEmpty()) {
                return BookAdapter.toDTOList(bookList);
            } else {
                throw new YearNotFoundException(bookFilter.getYear());
            }
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }
}
