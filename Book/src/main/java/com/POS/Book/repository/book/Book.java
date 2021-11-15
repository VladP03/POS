package com.POS.Book.repository.book;

import com.POS.Book.repository.book_author.BookAuthor;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Book")
@Table(name = "book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String isbn;

    @Column(unique = true)
    @NotEmpty(message = "Book's title can not be null or empty")
    private String title;

    @NotEmpty(message = "Book's publisher can not be null or empty")
    private String publisher;

    @NotNull(message = "Book's year can not be null")
    @Positive(message = "Book's year can not be negative")
    private Integer year;

    @NotEmpty(message = "Book's genre can not be null or empty")
    private String genre;

    @NotNull(message = "Book's price can not be null")
    @PositiveOrZero(message = "Book's price can not be negative")
    private Double price;

    @NotNull(message = "Book's stock can not be null")
    @PositiveOrZero(message = "Book's stock can not be negative")
    private Integer stock;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL
    )
    private List<BookAuthor> authorList = new ArrayList<>();
}
