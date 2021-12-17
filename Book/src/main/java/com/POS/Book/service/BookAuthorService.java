package com.POS.Book.service;

import com.POS.Book.model.DTO.AuthorDTO;
import com.POS.Book.model.DTO.BookAuthorDTO;
import com.POS.Book.model.DTO.BookDTO;
import com.POS.Book.model.adapter.AuthorAdapter;
import com.POS.Book.model.adapter.BookAdapter;
import com.POS.Book.repository.book_author.BookAuthor;
import com.POS.Book.repository.book_author.BookAuthorId;
import com.POS.Book.repository.book_author.BookAuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class BookAuthorService {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorRepository bookAuthorRepository;

    @Transactional
    public BookAuthorDTO postAuthorPerBook(String isbn, List<AuthorDTO> authorDTOList) {
        log.info(String.format("%s -> %s(isbn: %s, authorList: %s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, authorDTOList.toString()));

        BookDTO bookDTO = (BookDTO) bookService.getEntireBook(isbn);
        Integer index = getLastIndexOfBookAuthor(bookDTO);

        BookAuthorDTO bookAuthorDTO = new BookAuthorDTO();
        bookAuthorDTO.setBookIsbn(bookDTO.getIsbn());

        for (AuthorDTO authorDTO : authorDTOList) {
            BookAuthor bookAuthor = save(bookDTO, authorDTO, ++index);

            List<Long> newList = new ArrayList<>(bookAuthorDTO.getAuthorIdList());
            newList.add(bookAuthor.getAuthor().getId());

            bookAuthorDTO.setAuthorIdList(newList);

            List<Integer> newList2 = new ArrayList<>(bookAuthorDTO.getAuthorIndexList());
            newList2.add(bookAuthor.getIndex());
            bookAuthorDTO.setAuthorIndexList(newList2);
        }

        return bookAuthorDTO;
    }

    private BookAuthor save(BookDTO bookDTO, AuthorDTO authorDTO, Integer index) {
         return bookAuthorRepository.save(
                BookAuthor.builder()
                        .id(new BookAuthorId())
                        .book(BookAdapter.fromDTO(bookDTO))
                        .author(AuthorAdapter.fromDTO(authorService.createAuthor(authorDTO)))
                        .index(index)
                        .build()
        );
    }

    private Integer getLastIndexOfBookAuthor(BookDTO bookDTO) {
        return bookAuthorRepository.findLastAuthorIndexForBookAuthor(BookAdapter.fromDTO(bookDTO))
                .orElse(0);
    }
}
