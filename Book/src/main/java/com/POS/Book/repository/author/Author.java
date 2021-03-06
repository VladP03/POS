package com.POS.Book.repository.author;

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
@Entity(name = "Author")
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Author's first name can not be null")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Author's second name can not be null")
    @Column(name = "last_name")
    private String lastName;
}
