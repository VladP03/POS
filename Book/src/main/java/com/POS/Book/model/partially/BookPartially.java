package com.POS.Book.model.partially;

import com.POS.Book.model.Book;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPartially implements Book {

    private String isbn;
    private String title;
    private String publisher;
}
