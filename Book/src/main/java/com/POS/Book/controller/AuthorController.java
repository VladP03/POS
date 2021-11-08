package com.POS.Book.controller;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.service.AuthorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class AuthorController {

    private final AuthorService authorService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/author/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable(name = "ID") Long id) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.getAuthor(id));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDTO>> getAuthor(
            @RequestParam String name,
            @RequestParam(required = false) Boolean match
    ) {
        log.info(String.format("%s -> %s with name = %s and match = %b", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), name, match));

        AuthorFilter authorFilter = AuthorFilter.builder()
                .name(name)
                .match(match)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.getAuthors(authorFilter));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PostMapping(value = "/author",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        log.info(String.format("%s -> %s(%s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), authorDTO.toString()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorService.createAuthor(authorDTO));
    }
}
