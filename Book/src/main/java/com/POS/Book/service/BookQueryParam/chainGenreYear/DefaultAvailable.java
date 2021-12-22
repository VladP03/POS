package com.POS.Book.service.BookQueryParam.chainGenreYear;

import com.POS.Book.model.Book;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.Chain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<Book> run(BookFilter bookFilter, BookRepository bookRepository) {
        Integer page = bookFilter.getPage();
        Integer items_per_page = bookFilter.getItems_per_page();

        Pageable pageable = PageRequest.of(page, items_per_page);

        List<BookDTO> bookDTOList = BookAdapter.toDTOList(
                bookRepository.findAll(pageable).stream().collect(Collectors.toList()));

        return new ArrayList<>(bookDTOList);
    }
}
