package com.POS.Book.repository.book;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
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
}
