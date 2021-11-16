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
import java.util.Optional;

@Log4j2
@Service
@Validated
@RequiredArgsConstructor
public class BookAuthorService {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorRepository bookAuthorRepository;

    @Validated(OnCreate.class)
    public void postAuthorPerBook(String isbn, @Valid List<AuthorDTO> authorDTOList) {
        log.info(String.format("%s -> %s(isbn: %s, authorList: %s)", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), isbn, authorDTOList.toString()));

        BookDTO bookDTO = bookService.getBook(isbn);
        int index = getLastIndexOfBookAuthor(bookDTO);

        for (int i=0;i<authorDTOList.size();i++) {
            bookAuthorRepository.save(
                    BookAuthor.builder()
                            .id(new BookAuthorId())
                            .book(BookAdapter.fromDTO(bookDTO))
                            .author(AuthorAdapter.fromDTO(authorService.createAuthor(authorDTOList.get(i))))
                            .index(index+i+1)
                            .build()
            );
        }
    }

    private int getLastIndexOfBookAuthor(BookDTO bookDTO) {
        Optional<BookAuthor> bookAuthorByIndexDesc = bookAuthorRepository.findTopByBookOrderByIndexDesc(BookAdapter.fromDTO(bookDTO));

        if (bookAuthorByIndexDesc.isPresent()) {
            return bookAuthorByIndexDesc.get().getIndex();
        }

        return 0;
    }
}
