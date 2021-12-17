package com.POS.Book.model.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookAuthorDTO {

    private String bookIsbn;
    private List<Long> authorIdList = new ArrayList<>();
    private List<Integer> authorIndexList = new ArrayList<>();
}
