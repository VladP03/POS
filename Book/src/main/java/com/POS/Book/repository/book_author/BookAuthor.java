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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Book_Author")
@Table(name = "books_authors")
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookIsbn")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("authorId")
    private Author author;

    @NotNull(message = "Author's index can not be null")
    @Column(name = "author_index")
    private Integer index;
}
