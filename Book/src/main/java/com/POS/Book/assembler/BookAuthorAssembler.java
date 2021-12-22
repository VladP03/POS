package com.POS.Book.assembler;

import com.POS.Book.controller.AuthorController;
import com.POS.Book.controller.BookController;
import com.POS.Book.model.DTO.BookAuthorDTO;
import com.POS.Book.model.withoutPK.AuthorWithoutPk;
import com.POS.Book.model.withoutPK.BookWithoutPK;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookAuthorAssembler implements SimpleRepresentationModelAssembler<BookAuthorDTO> {

    @Override
    public void addLinks(EntityModel<BookAuthorDTO> resource) {
        String bookISBN = Objects.requireNonNull(resource.getContent()).getBookIsbn();

        // book
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

        // author
        for(Long authorId : resource.getContent().getAuthorIdList()) {
            resource.add(linkTo(methodOn(AuthorController.class).getAuthor(authorId))
                    .withRel("author collection")
                    .withType("GET"));

            resource.add(linkTo(methodOn(AuthorController.class).putAuthor(authorId, new AuthorWithoutPk()))
                    .withRel("author collection")
                    .withType("PUT"));

            resource.add(linkTo(methodOn(AuthorController.class).deleteAuthor(authorId))
                    .withRel("author collection")
                    .withType("DELETE"));
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<BookAuthorDTO>> resources) {

    }
}
