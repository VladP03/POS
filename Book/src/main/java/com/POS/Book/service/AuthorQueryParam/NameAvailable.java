package com.POS.Book.service.AuthorQueryParam;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.repository.author.AuthorRepository;
import com.POS.Book.service.exception.author.AuthorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class NameAvailable implements Chain {

    private Chain nextChain;

    @Override
    public void setNextChain(Chain nextInChain) {
        nextChain = nextInChain;
    }

    @Override
    public List<AuthorDTO> run(AuthorFilter authorFilter, AuthorRepository authorRepository) {
        String name = authorFilter.getName();

        return authorRepository.findByNamePartially(name)
                .stream()
                .map(AuthorAdapter::toDTO)
                .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                    if (result.isEmpty()) throw new AuthorNotFoundException(String.format("with name: %s", name));
                    return result;
                }));
    }
}
