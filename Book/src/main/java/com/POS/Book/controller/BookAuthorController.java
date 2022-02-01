package com.POS.Book.controller;

import com.POS.Book.assembler.BookAuthorAssembler;
import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.DTO.BookAuthorDTO;
import com.POS.Book.service.BookAuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class BookAuthorController {

    private final BookAuthorService bookAuthorService;
    private final BookAuthorAssembler bookAuthorAssembler;


    @PostMapping(value = "/{ISBN}/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<BookAuthorDTO>> postAuthorPerBook(
            @PathVariable(name = "ISBN") String isbn,
            @RequestBody List<AuthorDTO> authorDTOList) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        BookAuthorDTO bookAuthor = bookAuthorService.postAuthorPerBook(isbn, authorDTOList);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookAuthorAssembler.toModel(bookAuthor));
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
