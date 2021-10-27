package com.POS.Book.repository.book;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String isbn;

    private String title;
    private String publisher;
    private Integer year;
    private String gender;
    private Double price;
    private Integer stock;
}
