package com.facultate.POS.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {

    private String isbn;
    private String title;
    private Double price;
    private Integer stock;
}
