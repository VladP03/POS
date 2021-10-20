package com.POS.Book.service;

import com.POS.Book.model.AuthorDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.author.Author;
import com.POS.Book.repository.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorDTO getAuthor(Long id) {
        Optional<Author> author = Optional.ofNullable(authorRepository.findById(id)).orElseThrow(RuntimeException::new);

        return AuthorAdapter.toDTO(author.get());
    }

    @Validated(OnCreate.class)
    public AuthorDTO createAuthor(@Valid AuthorDTO authorDTO) {
        return AuthorAdapter.toDTO(authorRepository.save(AuthorAdapter.fromDTO(authorDTO)));
    }
}
