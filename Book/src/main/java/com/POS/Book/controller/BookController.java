package com.POS.Book.controller;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.service.AuthorService;
import com.POS.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
        log.info(String.format("%s -> getBook(%s)", this.getClass().getSimpleName(), isbn));

        return ResponseEntity.ok(bookService.getBook(isbn));
    }

    @PostMapping("/book")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info(String.format("%s -> createBook(%s)", this.getClass().getSimpleName(), bookDTO.toString()));

        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam Integer items_per_page) {
//        log.info(String.format("%s -> getBooks(%s)", this.getClass().getSimpleName(), bookDTO.toString()));

        BookFilter bookFilter = BookFilter.builder()
                .page(page)
                .items_per_page(items_per_page)
                .build();

        return ResponseEntity.ok(bookService.getBooks(bookFilter));
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
}
