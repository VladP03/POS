package com.POS.Book.service;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.repository.book_author.BookAuthor;
import com.POS.Book.repository.book_author.BookAuthorId;
import com.POS.Book.repository.book_author.BookAuthorRepository;
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
public class BookAuthorService {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorRepository bookAuthorRepository;

    @Validated(OnCreate.class)
    public void putAuthorPerBook(String isbn, @Valid List<AuthorDTO> authorDTOList) {
//        log.info(String.format(logInfoTemplate, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), authorDTO.toString()));

        BookDTO bookDTO = bookService.getBook(isbn);

        for (int i=0;i<authorDTOList.size();i++) {
            bookAuthorRepository.save(
                    BookAuthor.builder()
                            .id(new BookAuthorId())
                            .book(BookAdapter.fromDTO(bookDTO))
                            .author(AuthorAdapter.fromDTO(authorService.createAuthor(authorDTOList.get(i))))
                            .index(i+1)
                            .build()
            );
        }
    }
}