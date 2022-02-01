package com.POS.Book.service;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.model.withoutPK.AuthorWithoutPk;
import com.POS.Book.repository.author.Author;
import com.POS.Book.repository.author.AuthorRepository;
import com.POS.Book.service.AuthorQueryParam.ChainOfResponsability;
import com.POS.Book.service.exception.author.NotFound.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorDTO getAuthor(Long id) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return AuthorAdapter.toDTO(authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("with id %d", id))));
    }


    public List<AuthorDTO> getAuthors(AuthorFilter authorFilter) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return new ChainOfResponsability().getFirstChain().run(authorFilter, authorRepository);
    }


    @Validated(OnCreate.class)
    public AuthorDTO createAuthor(@Valid AuthorDTO authorDTO) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return AuthorAdapter.toDTO(authorRepository.save(AuthorAdapter.fromDTO(authorDTO)));
    }


    public void putAuthor(Long id, AuthorWithoutPk authorWithoutPk) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        AuthorDTO authorDTO = getAuthorById(id);

        BeanUtils.copyProperties(authorWithoutPk, authorDTO);

        authorRepository.save(AuthorAdapter.fromDTO(authorDTO));
    }


    public void deleteAuthor(Long id) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        AuthorDTO authorDTO = getAuthorById(id);

        authorRepository.delete(AuthorAdapter.fromDTO(authorDTO));
    }


    public AuthorDTO getAuthorById(Long id) {
        return AuthorAdapter.toDTO(authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id.toString())));
    }

    public Optional<AuthorDTO> findAuthorByFirstNameAndLastName(String firstName, String lastName) {
        Optional<Author> authorOptional = authorRepository.findByFirstNameAndLastName(firstName, lastName);

        if (authorOptional.isPresent()) {
            return Optional.of(AuthorAdapter.toDTO(authorOptional.get()));
        }

        return Optional.empty();
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Service %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
