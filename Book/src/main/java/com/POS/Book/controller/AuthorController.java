package com.POS.Book.controller;

import com.POS.Book.assembler.AuthorAssembler;
import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.model.withoutPK.AuthorWithoutPk;
import com.POS.Book.service.AuthorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorAssembler authorAssembler;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/author/{ID}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AuthorDTO>> getAuthor(@PathVariable(name = "ID") Long id) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        AuthorDTO authorDTO = authorService.getAuthor(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorAssembler.toModel(authorDTO));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/authors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<AuthorDTO>>> getAuthor(
            @RequestParam String name,
            @RequestParam(required = false) Boolean match) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        AuthorFilter authorFilter = AuthorFilter.builder()
                .name(name)
                .match(match)
                .build();

        List<AuthorDTO> authorDTOList = authorService.getAuthors(authorFilter);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorAssembler.toCollectionModel(authorDTOList));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @PutMapping(value = "/author/{ID}")
    public ResponseEntity<Void> putAuthor(
            @PathVariable(name = "ID") Long id,
            @Valid @RequestBody AuthorWithoutPk authorWithoutPk) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        authorService.putAuthor(id, authorWithoutPk);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @DeleteMapping(value = "/author/{ID}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable(name = "ID") Long id) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        authorService.deleteAuthor(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
