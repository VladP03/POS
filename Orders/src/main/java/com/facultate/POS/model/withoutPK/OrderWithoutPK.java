package com.facultate.POS.model.withoutPK;

import com.facultate.POS.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class OrderWithoutPK {

    private List<Book> items;
}
