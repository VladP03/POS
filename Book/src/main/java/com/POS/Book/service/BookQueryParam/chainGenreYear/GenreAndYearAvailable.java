package com.POS.Book.service.BookQueryParam.chainGenreYear;

import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class GenreAndYearAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (isNotNull(bookFilter.getYear(), bookFilter.getGenre())) {
            String genre = bookFilter.getGenre();
            Integer year = bookFilter.getYear();

            Integer page = bookFilter.getPage();
            Integer items_per_page = bookFilter.getItems_per_page();

            Pageable pageable = PageRequest.of(page, items_per_page);
            return BookAdapter.toDTOList(bookRepository.findByGenreAndYear(genre, year, pageable).stream().collect(Collectors.toList()));
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }

    private boolean isNotNull(Integer integerValue, String stringValue) {
        return integerValue != null && stringValue != null;
    }
}
