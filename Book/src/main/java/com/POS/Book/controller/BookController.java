package com.POS.Book.controller;

import com.POS.Book.assembler.BookAssembler;
import com.POS.Book.model.Book;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.filter.BookFilter;
import com.POS.Book.model.withoutPK.BookWithoutPK;
import com.POS.Book.service.BookService;
import com.POS.Book.service.exception.token.EmptyTokenException;
import com.POS.Book.service.exception.token.InvalidTokenException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcollection")
public class BookController {

    private static final String TOKEN_HEADER = "Authorization";

    private final BookService bookService;
    private final BookAssembler bookAssembler;
    private final HttpServletRequest httpServletRequest;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/book/{ISBN}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Book>> getBook(
            @PathVariable(name = "ISBN") String isbn,
            @RequestParam(defaultValue = "true") Boolean verbose) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String token = httpServletRequest.getHeader(TOKEN_HEADER);
        checkIfTokenIsPresent(token);
        CallValidateTokenEndpoint(token);

        Book books = bookService.getBook(isbn, verbose);

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toModel(books));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @GetMapping(value = "/books",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Book>>> getBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer items_per_page,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "true") Boolean verbose) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String token = httpServletRequest.getHeader(TOKEN_HEADER);
        checkIfTokenIsPresent(token);
        CallValidateTokenEndpoint(token);

        BookFilter bookFilter = BookFilter.builder()
                .page(page)
                .items_per_page(items_per_page)
                .genre(genre)
                .year(year)
                .build();

        List<Book> books = bookService.getBooks(bookFilter, verbose);

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toCollectionModel(books));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PostMapping(value = "/book",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Book>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String token = httpServletRequest.getHeader(TOKEN_HEADER);
        checkIfTokenIsPresent(token);
        CallValidateTokenEndpoint(token);

        Book bookCreated = bookService.createBook(bookDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toModel(bookCreated));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    @PutMapping(value = "/book/{ISBN}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Book>> putBook(
            @PathVariable(name = "ISBN") String isbn,
            @Valid @RequestBody BookWithoutPK bookWithoutPK) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String token = httpServletRequest.getHeader(TOKEN_HEADER);
        checkIfTokenIsPresent(token);
        CallValidateTokenEndpoint(token);

        Optional<Book> bookOptional = bookService.putBook(isbn, bookWithoutPK);

        if (bookOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookAssembler.toModel(bookOptional.get()));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    @DeleteMapping("/{ISBN}")
    public ResponseEntity<Void> deleteBook(@PathVariable String ISBN) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String token = httpServletRequest.getHeader(TOKEN_HEADER);
        checkIfTokenIsPresent(token);
        CallValidateTokenEndpoint(token);

        bookService.deleteBook(ISBN);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    private void checkIfTokenIsPresent(String token) {
        try {
            if (token.isEmpty()) {
                throw new EmptyTokenException("Authorization header is empty");
            }

            if (!token.startsWith("Bearer")) {
                throw new InvalidTokenException("Invalid token syntax");
            }
        } catch (NullPointerException exception) {
            throw new EmptyTokenException("Authorization header is null");
        }
    }


    private void CallValidateTokenEndpoint(String token) {
        try {
            SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage soapRequest = MessageFactory.newInstance().createMessage();
            SOAPPart soapPart = soapRequest.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();

            SOAPBodyElement soapBodyElement = body.addBodyElement(
                    envelope.createName("Request-ValidateToken", "gs", "http://com.pos.JWT/Token"));
            SOAPElement soapElement = soapBodyElement.addChildElement(
                    envelope.createName("token", "gs", "http://com.pos.JWT/Token"));

            soapElement.addTextNode(token);

            MimeHeaders headers = soapRequest.getMimeHeaders();
            headers.setHeader("Content-Type", MediaType.TEXT_XML_VALUE);
            headers.setHeader("Authorization", String.format("Bearer %s", token));

            soapRequest.saveChanges();

            SOAPMessage soapResponse = connection.call(soapRequest, "http://localhost:8090/Token");

            if (!soapResponse.getSOAPBody().getFirstChild().getFirstChild().getLocalName().equals("sub")) {
                throw new InvalidTokenException("Token invalid");
            }

            connection.close();
        } catch (SOAPException exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Something happened calling SOAP endpoint");
        }
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
