package com.facultate.POS.model.DTO;

import com.facultate.POS.model.Book;
import com.facultate.POS.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderDTO {

    @NotNull(message = "Order's id can not be null")
    private String id;

    @NotNull(message = "Order's status can not be null")
    private OrderStatus status;

    @Builder.Default
    private List<Book> items = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy - hh:mm:ssa")
    private LocalDateTime date = LocalDateTime.now();
}
