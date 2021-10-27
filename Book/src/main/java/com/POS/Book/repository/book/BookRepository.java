package com.POS.Book.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    boolean existsByTitle(String title);

    List<Book> findByTitleContaining(String title);
    List<Book> findByPrice(Double price);
    List<Book> findByYear(Integer year);
}
