package com.POS.Book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@AllArgsConstructor
public class BookDTO {

    @NotNull(message = "Book's ISBN can not be null")
    private String isbn;

    @NotNull(message = "Book's title can not be null")
    private String title;

    @NotNull(message = "Book's publisher can not be null")
    private String publisher;

    @NotNull(message = "Book's year can not be null")
    private Integer year;

    @NotNull(message = "Book's gender can not be null")
    private String gender;

    @NotNull(message = "Book's price can not be null")
    @PositiveOrZero(message = "Book's price can not be negative")
    private Double price;

    @NotNull(message = "Book's stock can not be null")
    @PositiveOrZero(message = "Book's stock can not be negative")
    private Integer stock;
}
