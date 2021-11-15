package com.POS.Book.repository.author;


import com.POS.Book.repository.book_author.BookAuthor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Author")
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Author's first name can not be null")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Author's second name can not be null")
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<BookAuthor> bookList = new ArrayList<>();
}
