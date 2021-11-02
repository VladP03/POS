package com.POS.Book.service.BookQueryParam.chainGenreYear;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;

import java.util.List;

public class GenreAndYearAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (isNotNull(bookFilter.getYear(), bookFilter.getGenre())) {
            return BookAdapter.toDTOList(bookRepository.findByGenreAndYear(bookFilter.getGenre(), bookFilter.getYear()));
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }

    private boolean isNotNull(Integer integerValue, String stringValue) {
        return integerValue != null && stringValue != null;
    }
}
