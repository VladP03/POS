package com.POS.Book.service.BookQueryParam.chainGenreYear;

import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;
import com.POS.Book.service.exception.book.NotFound.YearNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class YearAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        if (isNotNull(bookFilter.getYear())) {
            Integer year = bookFilter.getYear();

            Integer page = bookFilter.getPage();
            Integer items_per_page = bookFilter.getItems_per_page();

            Pageable pageable = PageRequest.of(page, items_per_page);
            List<BookDTO> bookDTOList = BookAdapter.toDTOList(bookRepository.findByYear(year, pageable).stream().collect(Collectors.toList()));

            if (bookDTOList.isEmpty()) {
                throw new YearNotFoundException(year.toString());
            }

            return bookDTOList;
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }

    private boolean isNotNull(Integer value) {
        return value != null;
    }
}
