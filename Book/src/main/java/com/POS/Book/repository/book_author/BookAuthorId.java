package com.POS.Book.repository.book_author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorId implements Serializable {
    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "author_id")
    private Long authorId;
}
