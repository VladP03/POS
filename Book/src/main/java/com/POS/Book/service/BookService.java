package com.POS.Book.service;

import com.POS.Book.model.Book;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.partially.BookPartially;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.ChainOfResponsability;
import com.POS.Book.service.exception.book.NotFound.BookNotFoundException;
import com.POS.Book.service.exception.book.NotFound.IsbnNotFoundException;
import com.POS.Book.service.exception.book.unique.TitleUniqueException;
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

    public Book getBook(String isbn, Boolean verbose) {
        log.info(String.format("%s -> %s(%s) and verbose = %b", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, verbose));

        BookDTO bookDTO = getBook(isbn);

        if (verbose != null && verbose == true) {
            return BookPartially.builder()
                    .isbn(bookDTO.getIsbn())
                    .title(bookDTO.getTitle())
                    .publisher(bookDTO.getPublisher())
                    .build();
        }

        return bookDTO;
    }

    public BookDTO getBook(String isbn) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn));

        return BookAdapter.toDTO(bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IsbnNotFoundException(isbn)));
    }

    @Validated(OnCreate.class)
    public BookDTO createBook(@Valid BookDTO bookDTO) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), bookDTO.toString()));

        checkTitleForUnicity(bookDTO.getTitle());

        return BookAdapter.toDTO(bookRepository.save(BookAdapter.fromDTO(bookDTO)));
    }

    public List<BookDTO> getBooks(BookFilter bookFilter) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), bookFilter.toString()));

        List<BookDTO> bookDTOList = new ChainOfResponsability().getFirstChain().run(bookFilter, bookRepository);

        if (bookDTOList.isEmpty()) {
            throw new BookNotFoundException(bookFilter.toString());
        }

        return bookDTOList;
    }

    public void deleteBook(String ISBN) {
        BookDTO bookDTOToDelete = getBook(ISBN);

        bookRepository.delete(BookAdapter.fromDTO(bookDTOToDelete));
    }

    private void checkTitleForUnicity(String title) {
        if (titleIsNotUnique(title)) {
            throw new TitleUniqueException(title);
        }
    }

    private boolean titleIsNotUnique(String title) {
        return bookRepository.existsByTitle(title);
    }

}
