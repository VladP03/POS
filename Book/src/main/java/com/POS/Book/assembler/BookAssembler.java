package com.POS.Book.assembler;

import com.POS.Book.controller.BookController;
import com.POS.Book.model.Book;
import com.POS.Book.model.withoutPK.BookWithoutPK;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookAssembler implements SimpleRepresentationModelAssembler<Book> {

    @Override
    public void addLinks(EntityModel<Book> resource) {
        String bookISBN = Objects.requireNonNull(resource.getContent()).getIsbn();

        resource.add(linkTo(methodOn(BookController.class).getBook(bookISBN, true))
                .withRel("book collection")
                .withType("GET"));

        resource.add(linkTo(methodOn(BookController.class).getBook(bookISBN, false))
                .withRel("book collection")
                .withType("GET"));

        resource.add(linkTo(methodOn(BookController.class).putBook(bookISBN, new BookWithoutPK()))
                .withRel("book collection")
                .withType("PUT"));

        resource.add(linkTo(methodOn(BookController.class).deleteBook(bookISBN))
                .withRel("book collection")
                .withType("DELETE"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Book>> resources) {

    }
}
