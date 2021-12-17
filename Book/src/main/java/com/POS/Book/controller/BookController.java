package com.POS.Book.controller;

import com.POS.Book.assembler.BookAssembler;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.withoutPK.BookWithoutPK;
import com.POS.Book.service.BookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class BookController {

    private final BookService bookService;
    private final BookAssembler bookAssembler;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/book/{ISBN}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBook(
            @PathVariable(name = "ISBN") String isbn,
            @RequestParam(defaultValue = "false") Boolean verbose) {
        log.info(String.format("%s -> %s(%s) & verbose = %b", this.getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, verbose));

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toModel(BookAdapter.fromDTO((BookDTO) bookService.getBook(isbn, verbose))));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/books",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer items_per_page,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year) {

        BookFilter bookFilter = BookFilter.builder()
                .page(page)
                .items_per_page(items_per_page)
                .genre(genre)
                .year(year)
                .build();

        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), bookFilter.toString()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toCollectionModel(BookAdapter.fromDTOList(bookService.getBooks(bookFilter))));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PostMapping(value = "/book",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), bookDTO.toString()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toModel(BookAdapter.fromDTO(bookService.createBook(bookDTO))));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PutMapping(value = "/book/{ISBN}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putBook(
            @PathVariable String ISBN,
            @Valid @RequestBody BookWithoutPK bookWithoutPK) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), bookWithoutPK.toString()));

        Optional<BookDTO> bookDTOOptional = bookService.putBook(ISBN, bookWithoutPK);

        if (bookDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toModel(BookAdapter.fromDTO(bookDTOOptional.get())));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @DeleteMapping("/{ISBN}")
    public ResponseEntity<?> deleteBook(@PathVariable String ISBN) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), ISBN));

        bookService.deleteBook(ISBN);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
