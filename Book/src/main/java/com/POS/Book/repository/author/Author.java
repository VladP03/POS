package com.POS.Book.repository.author;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "author")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Author's first name can not be null")
    private String firstName;

    @NotNull(message = "Author's second name can not be null")
    private String lastName;
}
