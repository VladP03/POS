package com.POS.Book.controller;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.partially.BookPartially;
import com.POS.Book.service.AuthorService;
import com.POS.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;


    @GetMapping(value = "/book/{ISBN}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getBook(@PathVariable(name = "ISBN") String isbn) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBook(isbn));
    }

    @GetMapping(value = "/book/{ISBN}", params = "verbose", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookPartially> getBook(
            @PathVariable(name = "ISBN") String isbn,
            @RequestParam Boolean verbose) {
        log.info(String.format("%s -> %s(%s) & verbose = %b", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, verbose));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBook(isbn, verbose));
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer items_per_page,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year) {
        log.info(String.format("%s -> %s ->", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName()));

        BookFilter bookFilter = BookFilter.builder()
                .page(page)
                .items_per_page(items_per_page)
                .genre(genre)
                .year(year)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBooks(bookFilter));
    }

    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), bookDTO.toString()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBook(bookDTO));
    }
}
