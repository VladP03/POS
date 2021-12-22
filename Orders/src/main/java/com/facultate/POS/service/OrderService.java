package com.facultate.POS.service;

import com.facultate.POS.model.Adapter.OrderAdapter;
import com.facultate.POS.model.Book;
import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.model.OrderStatus;
import com.facultate.POS.repository.Order.Order;
import com.facultate.POS.repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public Order createOrder(List<Book> items) {
        OrderDTO orderDTO = OrderDTO.builder()
                .date(LocalDate.now())
                .status(OrderStatus.PENDING)
                .items(items)
                .build();

        return orderRepository.save(OrderAdapter.fromDTO(orderDTO));
    }
}
