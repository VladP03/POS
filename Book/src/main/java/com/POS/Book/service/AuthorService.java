package com.POS.Book.service;

import com.POS.Book.model.AuthorDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.author.AuthorRepository;
import com.POS.Book.service.exception.author.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorDTO getAuthor(Long id) {
        log.info(String.format("%s -> getAuthor(%s)", this.getClass().getSimpleName(), id));

        return AuthorAdapter.toDTO(authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id.toString())));
    }

    @Validated(OnCreate.class)
    public AuthorDTO createAuthor(@Valid AuthorDTO authorDTO) {
        log.info(String.format("%s -> createAuthor(%s)", this.getClass().getSimpleName(), authorDTO.toString()));

        return AuthorAdapter.toDTO(authorRepository.save(AuthorAdapter.fromDTO(authorDTO)));
    }
}
