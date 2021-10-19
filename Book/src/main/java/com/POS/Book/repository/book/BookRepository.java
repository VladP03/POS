package com.POS.Book.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    List<Book> findByTitleContaining(String title);
    List<Book> findByPrice(Double price);
    List<Book> findByYear(Integer year);
}
