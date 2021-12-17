package com.POS.Book.model.withoutPK;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthorWithoutPk {

    @NotNull(message = "Author's first name can not be null")
    private String firstName;

    @NotNull(message = "Author's second name can not be null")
    private String lastName;
}
