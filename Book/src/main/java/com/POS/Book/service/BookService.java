package com.POS.Book.service;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.book.Book;
import com.POS.Book.repository.book.BookRepository;
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

    public List<BookDTO> getBook() {
        Optional<List<Book>> bookOptional = Optional.ofNullable(bookRepository.findAll());

        if (bookOptional.isPresent()) {
            return BookAdapter.toDTOList(bookOptional.get());
        } else {
            throw new RuntimeException();
        }
    }

    @Validated(OnCreate.class)
    public BookDTO createBook(@Valid BookDTO bookDTO) {
        return BookAdapter.toDTO(bookRepository.save(BookAdapter.fromDTO(bookDTO)));
    }
}
