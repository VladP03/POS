package com.POS.Book.repository.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    boolean existsByTitle(String title);

    Book getByTitle(String title);

    List<Book> findByGenre(String genre, Pageable pageable);

    List<Book> findByYear(Integer year, Pageable pageable);

    List<Book> findByGenreAndYear(String genre, Integer year, Pageable pageable);
}
