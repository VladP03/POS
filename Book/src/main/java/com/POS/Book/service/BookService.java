package com.POS.Book.service;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.book.Book;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.getQueryParam.ChainOfResponsability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<BookDTO> getBook(BookFilter bookFilter) {
        if (areAllFieldsNull(bookFilter)) {
            return BookAdapter.toDTOList(bookRepository.findAll());
        } else {
            return new ChainOfResponsability().getFirstChain().run(bookFilter, bookRepository);
        }
    }

    @Validated(OnCreate.class)
    public BookDTO createBook(@Valid BookDTO bookDTO) {
        return BookAdapter.toDTO(bookRepository.save(BookAdapter.fromDTO(bookDTO)));
    }

    private boolean areAllFieldsNull(BookFilter bookFilter) {
        return bookFilter.getIsbn() == null && bookFilter.getTitle() == null && bookFilter.getYear() == null && bookFilter.getPrice() == null;
    }
}
