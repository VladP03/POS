package com.POS.Book.service;

import com.POS.Book.model.Book;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.withoutPK.BookWithoutPK;
import com.POS.Book.repository.book.BookRepository;
import com.POS.Book.service.BookQueryParam.ChainOfResponsability;
import com.POS.Book.service.exception.book.NotFound.IsbnNotFoundException;
import com.POS.Book.service.exception.book.unique.TitleUniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public Book getBook(String isbn, Boolean verbose) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        if (verbose == true) {
            return getEntireBook(isbn);
        }

        return getPartialBook(isbn);
    }


    public List<Book> getBooks(BookFilter bookFilter, Boolean verbose) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        List<Book> books = new ChainOfResponsability().getFirstChain().run(bookFilter, bookRepository);

        if (verbose == true) {
            return books;
        }

        return BookAdapter.toPartialList(books);
    }


    public Book createBook(BookDTO bookDTO) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        checkTitleForUniqueness(bookDTO.getTitle());

        return BookAdapter.toDTO(bookRepository.save(BookAdapter.fromDTO(bookDTO)));
    }


    public Optional<Book> putBook(String isbn, BookWithoutPK bookWithoutPK) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        if (checkIfIsbnExists(isbn)) {
            updateBook(isbn, bookWithoutPK);
            return Optional.empty();
        }

        return Optional.of(createNewBook(isbn, bookWithoutPK));
    }


    public void deleteBook(String isbn) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        Book bookToDelete = getEntireBook(isbn);

        bookRepository.delete(BookAdapter.fromDTO((BookDTO) bookToDelete));
    }


    /**
     * @param isbn
     * @return An entire book founded after isbn
     * @throws IsbnNotFoundException
     */
    public Book getEntireBook(String isbn) {
        return BookAdapter.toDTO(bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IsbnNotFoundException(isbn)));
    }


    /**
     * @param isbn
     * @return A partial book founded after isbn
     * @throws IsbnNotFoundException
     */
    public Book getPartialBook(String isbn) {
        BookDTO book = (BookDTO) getEntireBook(isbn);

        return BookAdapter.toPartial(book);
    }


    /**
     * Checks in DB if title exists
     *
     * @param title
     * @throws TitleUniqueException
     */
    public void checkTitleForUniqueness(String title) {
        if (bookRepository.existsByTitle(title)) {
            throw new TitleUniqueException(title);
        }
    }


    /**
     * Update an existing book in DB
     *
     * @param isbn
     * @param bookWithoutPK
     */
    private void updateBook(String isbn, BookWithoutPK bookWithoutPK) {
        Book bookToUpdate = getEntireBook(isbn);

        BeanUtils.copyProperties(bookWithoutPK, bookToUpdate);

        createBook((BookDTO) bookToUpdate);
    }


    /**
     * Create a new book in DB
     *
     * @param isbn
     * @param bookWithoutPK
     */
    private Book createNewBook(String isbn, BookWithoutPK bookWithoutPK) {
        BookDTO book = BookDTO.builder()
                .isbn(isbn)
                .title(bookWithoutPK.getTitle())
                .publisher(bookWithoutPK.getPublisher())
                .year(bookWithoutPK.getYear())
                .genre(bookWithoutPK.getGenre())
                .price(bookWithoutPK.getPrice())
                .stock(bookWithoutPK.getStock())
                .build();

        return createBook(book);
    }


    public boolean checkIfIsbnExists(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Service %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
