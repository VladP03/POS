package com.facultate.POS.Book.controller;

import com.facultate.POS.Book.model.BookDTO;
import com.facultate.POS.Book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
