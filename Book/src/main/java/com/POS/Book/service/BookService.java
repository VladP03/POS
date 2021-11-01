package com.POS.Book.service;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.exception.book.NotFound.IsbnNotFoundException;
import com.POS.Book.service.exception.book.unique.TitleUniqueException;
import com.POS.Book.service.queryParamCOP.COPPageDisplay;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDTO getBook(String isbn) {
        log.info(String.format("%s -> getBook(%s)", this.getClass().getSimpleName(), isbn));

        return BookAdapter.toDTO(bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IsbnNotFoundException(isbn)));
    }

    @Validated(OnCreate.class)
    public BookDTO createBook(@Valid BookDTO bookDTO) {
        log.info(String.format("%s -> createBook(%s)", this.getClass().getSimpleName(), bookDTO.toString()));

        checkTitleForUnicity(bookDTO.getTitle());

        return BookAdapter.toDTO(bookRepository.save(BookAdapter.fromDTO(bookDTO)));
    }

    public List<BookDTO> getBooks(BookFilter bookFilter) {
        return new COPPageDisplay().getFirstChain().run(bookFilter, bookRepository);
    }

    private void checkTitleForUnicity(String title) {
        if (titleIsNotUnique(title)) {
            throw new TitleUniqueException(title);
        }
    }

    private boolean titleIsNotUnique(String title) {
        log.info(String.format("Check Title: %s for unicity", title));

        return bookRepository.existsByTitle(title);
    }



//    public List<BookDTO> getBooks(BookFilter bookFilter) {
//        if (areAllFieldsNull(bookFilter)) {
//            return BookAdapter.toDTOList(bookRepository.findAll());
//        } else {
//            return new ChainOfResponsability().getFirstChain().run(bookFilter, bookRepository);
//        }
//    }
//
//
//    private boolean areAllFieldsNull(BookFilter bookFilter) {
//        return bookFilter.getTitle() == null && bookFilter.getYear() == null && bookFilter.getPrice() == null;
//    }
}
