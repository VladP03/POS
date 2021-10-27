package com.POS.Book.model.adapter;

import com.POS.Book.model.AuthorDTO;
import com.POS.Book.repository.author.Author;

import java.util.List;
import java.util.stream.Collectors;

public final class AuthorAdapter {

    private AuthorAdapter() {
    }

    public static AuthorDTO toDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .secondName(author.getSecondName())
                .build();
    }

    public static Author fromDTO(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .firstName(authorDTO.getFirstName())
                .secondName(authorDTO.getSecondName())
                .build();
    }

    public static List<AuthorDTO> toDTOList(List<Author> authorList) {
        return authorList.stream()
                .map(AuthorAdapter::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Author> fromDTOList(List<AuthorDTO> authorDTOList) {
        return authorDTOList.stream()
                .map(AuthorAdapter::fromDTO)
                .collect(Collectors.toList());
    }

}
