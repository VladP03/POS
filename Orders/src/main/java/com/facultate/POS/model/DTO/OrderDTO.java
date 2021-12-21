package com.facultate.POS.model.DTO;

import com.facultate.POS.model.Book;
import com.facultate.POS.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderDTO {

    private String id;
    private LocalDate date;
    private OrderStatus status;

    @Builder.Default
    private List<Book> items = new ArrayList<>();
}
