package com.facultate.POS.repository.Order;

import com.facultate.POS.model.Book;
import com.facultate.POS.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document
public class Order {

    @Id
    private String id;

    private LocalDate date;
    private List<Book> items;
    private OrderStatus status;
}
