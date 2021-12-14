package com.POS.Book.controller;

import com.POS.Book.model.Book;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.service.AuthorService;
import com.POS.Book.service.BookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/book/{ISBN}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBook(
            @PathVariable(name = "ISBN") String isbn,
            @RequestParam(defaultValue = "false") Boolean verbose) {
        log.info(String.format("%s -> %s(%s) & verbose = %b", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, verbose));

        Book book = bookService.getBook(isbn, verbose);

        ResponseEntity<Book> responseEntity;
        EntityModel<Book> resource;

        responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBook(isbn, verbose));
        resource = EntityModel.of(Objects.requireNonNull(responseEntity.getBody()));

        if (verbose == true) {
            book = bookService.getBook(isbn, false);
        }

        Link selfLink = linkTo(methodOn(BookController.class).getBook(isbn, verbose)).withSelfRel().withType("GET");
        Link optionGET = linkTo(methodOn(BookController.class).getBook(isbn, !verbose)).withRel("book collection").withType("GET");
        Link optionPUT = linkTo(methodOn(BookController.class).putBook((BookDTO) book)).withRel("book collection").withType("PUT");
        Link optionDELETE = linkTo(methodOn(BookController.class).deleteBook(((BookDTO) book).getIsbn())).withRel("book collection").withType("DELETE");
        resource.add(selfLink);
        resource.add(optionGET);
        resource.add(optionPUT);
        resource.add(optionDELETE);

        return new ResponseEntity<>(resource, responseEntity.getStatusCode());
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

        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), bookFilter.toString()));

        ResponseEntity<?> responseEntity;
        CollectionModel<?> resource;

        List<BookDTO> bookDTOList = bookService.getBooks(bookFilter);

        List<EntityModel<BookDTO>> bookDTOListModified = bookDTOList.stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(BookController.class).getBook(book.getIsbn(), false)).withSelfRel().withType("GET"),
                        linkTo(methodOn(BookController.class).getBook(book.getIsbn(), true)).withRel("book collection").withType("GET"),
                        linkTo(methodOn(BookController.class).putBook(book)).withRel("book collection").withType("PUT"),
                        linkTo(methodOn(BookController.class).deleteBook(book.getIsbn())).withRel("book collection").withType("DELETE")))
                .collect(Collectors.toList());

        responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(bookDTOListModified);

        resource = CollectionModel.of(Collections.singleton(Objects.requireNonNull(responseEntity.getBody())));

        Link selfLink = linkTo(methodOn(BookController.class).getBooks(page, items_per_page, genre, year)).withSelfRel();
        resource.add(selfLink.expand());

        return new ResponseEntity<>(resource, responseEntity.getStatusCode());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PostMapping(value = "/book",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), bookDTO.toString()));

        BookDTO bookDTCreated = bookService.createBook(bookDTO);

        ResponseEntity<BookDTO> responseEntity;
        EntityModel<BookDTO> resource;

        responseEntity = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookDTCreated);
        resource = EntityModel.of(Objects.requireNonNull(responseEntity.getBody()));

        Link selfLink = linkTo(methodOn(BookController.class).getBook(bookDTCreated.getIsbn(), true)).withRel("book collection").withType("GET");
        Link optionGET = linkTo(methodOn(BookController.class).getBook(bookDTCreated.getIsbn(), false)).withRel("book collection").withType("GET");
        Link optionPUT = linkTo(methodOn(BookController.class).putBook(bookDTCreated)).withRel("book collection").withType("PUT");
        Link optionDELETE = linkTo(methodOn(BookController.class).deleteBook((bookDTCreated.getIsbn()))).withRel("book collection").withType("DELETE");
        resource.add(selfLink);
        resource.add(optionGET);
        resource.add(optionPUT);
        resource.add(optionDELETE);

        return new ResponseEntity<>(resource, responseEntity.getStatusCode());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PutMapping(value = "/book",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), bookDTO.toString()));

        Optional<BookDTO> bookDTOOptional = bookService.putBook(bookDTO);

        if (bookDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ResponseEntity<BookDTO> responseEntity;
        EntityModel<BookDTO> resource;

        responseEntity = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookDTOOptional.get());

        resource = EntityModel.of(Objects.requireNonNull(responseEntity.getBody()));

        Link selfLink = linkTo(methodOn(BookController.class).getBook(bookDTOOptional.get().getIsbn(), true)).withRel("book collection").withType("GET");
        Link optionGET = linkTo(methodOn(BookController.class).getBook(bookDTOOptional.get().getIsbn(), false)).withRel("book collection").withType("GET");
        Link optionPUT = linkTo(methodOn(BookController.class).putBook(bookDTOOptional.get())).withRel("book collection").withType("PUT");
        Link optionDELETE = linkTo(methodOn(BookController.class).deleteBook((bookDTOOptional.get().getIsbn()))).withRel("book collection").withType("DELETE");
        resource.add(selfLink);
        resource.add(optionGET);
        resource.add(optionPUT);
        resource.add(optionDELETE);

        return new ResponseEntity<>(resource, responseEntity.getStatusCode());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @DeleteMapping("/{ISBN}")
    public ResponseEntity<?> deleteBook(@PathVariable String ISBN) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ISBN));

        bookService.deleteBook(ISBN);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
