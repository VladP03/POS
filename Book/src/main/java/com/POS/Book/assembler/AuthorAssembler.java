package com.POS.Book.assembler;

import com.POS.Book.controller.AuthorController;
import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.withoutPK.AuthorWithoutPk;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorAssembler implements SimpleRepresentationModelAssembler<AuthorDTO> {

    @Override
    public void addLinks(EntityModel<AuthorDTO> resource) {
        Long authorId = Objects.requireNonNull(resource.getContent()).getId();

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

    @Override
    public void addLinks(CollectionModel<EntityModel<AuthorDTO>> resources) {

    }
}
