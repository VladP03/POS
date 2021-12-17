package com.POS.Book.model.partial;

import com.POS.Book.model.Book;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPartial extends Book {

    private String isbn;
    private String title;
    private String publisher;
}
