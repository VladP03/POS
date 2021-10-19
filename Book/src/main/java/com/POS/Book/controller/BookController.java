package com.POS.Book.controller;

import com.POS.Book.model.BookDTO;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBook(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double price) {

        BookFilter bookFilter = BookFilter.builder()
                .isbn(isbn)
                .title(title)
                .year(year)
                .price(price)
                .build();

        return ResponseEntity.ok(bookService.getBook(bookFilter));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }
}
