package com.POS.Book.service;

import com.POS.Book.model.AuthorDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.author.AuthorRepository;
import com.POS.Book.service.AuthorQueryParam.ChainOfResponsability;
import com.POS.Book.service.exception.author.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private static final String logInfoTemplate = "%s -> %s(%s)";

    public AuthorDTO getAuthor(Long id) {
        log.info(String.format(logInfoTemplate, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), id));

        return AuthorAdapter.toDTO(authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id.toString())));
    }

    public List<AuthorDTO> getAuthors(AuthorFilter authorFilter) {
        log.info(String.format(logInfoTemplate, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), authorFilter.toString()));

        return new ChainOfResponsability().getFirstChain().run(authorFilter, authorRepository);
    }


    @Validated(OnCreate.class)
    public AuthorDTO createAuthor(@Valid AuthorDTO authorDTO) {
        log.info(String.format(logInfoTemplate, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), authorDTO.toString()));

        return AuthorAdapter.toDTO(authorRepository.save(AuthorAdapter.fromDTO(authorDTO)));
    }
}
