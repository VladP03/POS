package com.POS.Book.controller;

import com.POS.Book.model.AuthorDTO;
import com.POS.Book.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/{ID}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable(name = "ID") Long id) {
        log.info(String.format("%s -> getAuthor(%s)", this.getClass().getSimpleName(), id));

        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @PostMapping("/author")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        log.info(String.format("%s -> createAuthor(%s)", this.getClass().getSimpleName(), authorDTO.toString()));

        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }
}
