package com.facultate.POS.model.Adapter;

import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.repository.Order.Order;

public class OrderAdapter {

    public static Order fromDTO(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .date(orderDTO.getDate())
                .items(orderDTO.getItems())
                .status(orderDTO.getStatus())
                .build();
    }
}
