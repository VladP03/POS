package com.POS.Book.repository.book_author;

import com.POS.Book.repository.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    @Query(value = "SELECT author_index FROM books_authors ba WHERE  ba.book_isbn =:book ORDER BY author_index DESC LIMIT 1", nativeQuery = true)
    Optional<Integer> findLastAuthorIndexForBookAuthor(@Param("book") Book book);
}
