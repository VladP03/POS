package com.POS.Book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Builder
@Getter @Setter
@AllArgsConstructor
public class BookDTO {

//    @Null(message = "Book's ISBN can not be null", groups = {OnCreate.class})
//    @NotNull(message = "Book's ISBN must be null", groups = {OnUpdate.class})
    private String isbn;

    @NotNull(message = "Book's Title can not be null")
    private String title;

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
