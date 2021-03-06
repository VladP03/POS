package com.facultate.POS.model.Adapter;

import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.repository.Order.Order;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderAdapter {

    public static Order fromDTO(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .date(orderDTO.getDate())
                .items(orderDTO.getItems())
                .status(orderDTO.getStatus())
                .build();
    }

    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .items(order.getItems())
                .status(order.getStatus())
                .build();
    }

    public static List<OrderDTO> toListDTO(List<Order> orderList) {
        return orderList.stream().map(OrderAdapter::toDTO).collect(Collectors.toList());
    }
}
