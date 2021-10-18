package com.facultate.POS.Book.repository.book;

import com.facultate.POS.Book.repository.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String Title);
}
