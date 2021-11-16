package com.POS.Book.repository.book_author;

import com.POS.Book.repository.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Book> {

    Optional<BookAuthor> findTopByBookOrderByIndexDesc(Book book);
}
