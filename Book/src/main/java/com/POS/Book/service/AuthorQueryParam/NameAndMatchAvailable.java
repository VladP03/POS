package com.POS.Book.service.AuthorQueryParam;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.repository.author.Author;
import com.POS.Book.repository.author.AuthorRepository;
import com.POS.Book.service.exception.author.AuthorNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NameAndMatchAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<AuthorDTO> run(AuthorFilter authorFilter, AuthorRepository authorRepository) {
        String name = authorFilter.getName();
        Boolean match = authorFilter.getMatch();

        if (areNotNull(name, match) && match == true) {
            Author authorFounded = Optional.ofNullable(authorRepository.findByNameEquals(name))
                    .orElseThrow(() -> new AuthorNotFoundException(String.format("with name: %s", name)));

            return AuthorAdapter.toDTOList(Collections.singletonList(authorFounded));
        } else {
            return nextChain.run(authorFilter, authorRepository);
        }
    }

    private boolean areNotNull(String stringValue, Boolean booleanValue) {
        return stringValue != null && booleanValue != null;
    }
}
