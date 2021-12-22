package com.facultate.POS.repository.Order;

import com.facultate.POS.model.Book;
import com.facultate.POS.model.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document
public class Order {

    @Id
    private String id;

    @NotNull(message = "Order's status can not be null")
    private OrderStatus status;

    @NotNull(message = "Order's date can not be null")
    private LocalDate date;

    private List<Book> items;
}
