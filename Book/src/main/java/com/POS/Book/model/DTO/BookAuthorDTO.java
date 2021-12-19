package com.POS.Book.model.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BookAuthorDTO {

    private String bookIsbn;

    @Builder.Default
    private List<Long> authorIdList = new ArrayList<>();
    @Builder.Default
    private List<Integer> authorIndexList = new ArrayList<>();
}
