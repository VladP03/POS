package com.POS.Book.controller;

import com.POS.Book.model.BookDTO;
import com.POS.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public BookDTO getBook(@RequestParam String title) {
        return bookService.getBook(title);
    }

    @PostMapping
    public BookDTO createBook(@Valid @RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }
}
