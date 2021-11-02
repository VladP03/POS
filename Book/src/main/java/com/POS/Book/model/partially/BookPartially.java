package com.POS.Book.model.partially;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPartially {

    private String isbn;
    private String title;
    private String publisher;
}
