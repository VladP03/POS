package com.POS.Book.service.BookQueryParam.chainPageDisplay;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class ItemsAvailable implements Chain {

    private final int page = 0;
    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<BookDTO> run(BookFilter bookFilter, BookRepository bookRepository) {
        Integer items_per_page = bookFilter.getItems_per_page();

        if (isNotNull(items_per_page)) {
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
