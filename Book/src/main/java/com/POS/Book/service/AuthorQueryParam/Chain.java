package com.POS.Book.service.AuthorQueryParam;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.filter.AuthorFilter;
import com.POS.Book.repository.author.AuthorRepository;

import java.util.List;

public interface Chain {

    void setNextChain(Chain nextInChain);

    List<AuthorDTO> run(AuthorFilter authorFilter, AuthorRepository authorRepository);
}
