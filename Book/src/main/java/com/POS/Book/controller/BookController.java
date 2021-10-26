package com.POS.Book.controller;

import com.POS.Book.model.BookDTO;
import com.POS.Book.service.AuthorService;
import com.POS.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class BookController {

    private final BookService bookService;
//    private final AuthorService authorService;

    @GetMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getBook(@RequestParam String ISBN) {
        log.info(String.format("%s -> getBook(%s)", this.getClass().getSimpleName(), ISBN));

        return ResponseEntity.ok(bookService.getBook(ISBN));
    }

    @PostMapping("/book")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info(String.format("%s -> createBook(%s)", this.getClass().getSimpleName(), bookDTO.toString()));

        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

//
//    @GetMapping("/books")
//    public ResponseEntity<List<BookDTO>> getBooks(
//            @RequestParam(required = false) String title,
////            @RequestParam(required = false) String Publisher, TODO
////            @RequestParam(required = false) String gender, TODO
//            @RequestParam(required = false) Integer year,
//            @RequestParam(required = false) Double price) {
//
//        BookFilter bookFilter = BookFilter.builder()
//                .title(title)
//                .year(year)
//                .price(price)
//                .build();
//
//        return ResponseEntity.ok(bookService.getBooks(bookFilter));
//    }
//
//
//    @GetMapping("/authors")
//    public ResponseEntity<AuthorDTO> getAuthor(@RequestParam Long id) {
//        return ResponseEntity.ok(authorService.getAuthor(id));
//    }
//
//    @PostMapping("/author")
//    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
//        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
//    }
}
