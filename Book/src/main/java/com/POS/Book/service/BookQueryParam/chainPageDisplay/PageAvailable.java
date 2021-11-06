package com.POS.Book.service.BookQueryParam.chainPageDisplay;

import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class PageAvailable implements Chain {

    private final int items_per_page = 10;
    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        Integer page = bookFilter.getPage();

        if (isNotNull(page)) {
            Pageable pageable = PageRequest.of(page, items_per_page);
            return BookAdapter.toDTOList(bookRepository.findAll(pageable).stream().collect(Collectors.toList()));
        } else {
            return nextChain.run(bookFilter, bookRepository);
        }
    }

    private boolean isNotNull(Integer value) {
        return value != null;
    }
}
