package com.POS.Book.model;

import com.POS.Book.model.validation.OnCreate;
import com.POS.Book.model.validation.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Getter @Setter
@AllArgsConstructor
public class AuthorDTO {

    @Null(message = "Author's id can not be null", groups = {OnCreate.class})
    @NotNull(message = "Author's id must be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Author's first name can not be null")
    private String firstName;

    @NotNull(message = "Author's second name can not be null")
    private String secondName;
}
