package com.POS.Book.controller;

import com.POS.Book.model.AuthorDTO;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.service.AuthorService;
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
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/author/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable(name = "ID") Long id) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), id));

        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDTO>> getAuthor(
            @RequestParam String name,
            @RequestParam(required = false) Boolean match
    ) {
        log.info(String.format("%s -> %s", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName()));

        AuthorFilter authorFilter = AuthorFilter.builder()
                .name(name)
                .match(match)
                .build();

        return ResponseEntity.ok(authorService.getAuthors(authorFilter));
    }

    @PostMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        log.info(String.format("%s -> createAuthor(%s)", this.getClass().getSimpleName(), authorDTO.toString()));

        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }
}
