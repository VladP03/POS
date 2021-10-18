package com.facultate.POS.Book.repository.book;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Book")
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String isbn;

    private String title;
    private Integer year;
    private String gender;
    private Double price;
    private Integer stock;
}
