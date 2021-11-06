package com.POS.Book.model.adapter;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.repository.author.Author;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorAdapter {

    public static AuthorDTO toDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static Author fromDTO(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
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
