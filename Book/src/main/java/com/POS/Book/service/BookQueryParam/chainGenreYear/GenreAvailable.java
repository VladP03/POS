package com.POS.Book.service.BookQueryParam.chainGenreYear;

import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;

import java.util.List;

public class GenreAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (isNotNull(bookFilter.getGenre())) {
            List<BookDTO> bookDTOList = BookAdapter.toDTOList(bookRepository.findByGenre(bookFilter.getGenre()));

            return bookDTOList;
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }

    private boolean isNotNull(String value) {
        return value != null;
    }
}
