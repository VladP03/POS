package com.POS.Book.controller;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.service.BookAuthorService;
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
public class BookAuthorController {

    private final BookAuthorService bookAuthorService;


    @PostMapping(value = "/{ISBN}/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity putAuthorPerBook(
            @PathVariable(name = "ISBN") String isbn,
            @RequestBody @Valid List<AuthorDTO> authorDTOList) {

        log.info(String.format("%s -> %s(%s, %s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, authorDTOList.toString()));

        bookAuthorService.postAuthorPerBook(isbn, authorDTOList);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
