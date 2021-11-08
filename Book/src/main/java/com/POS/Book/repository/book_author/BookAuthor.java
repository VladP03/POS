package com.POS.Book.repository.book_author;

import com.POS.Book.repository.author.Author;
import com.POS.Book.repository.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "book_author")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Author author;

    @NotNull(message = "Author's index can not be null")
    private Integer position;
}
