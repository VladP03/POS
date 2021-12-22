package com.facultate.POS.service;

import com.facultate.POS.model.Adapter.OrderAdapter;
import com.facultate.POS.model.Book;
import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.model.OrderStatus;
import com.facultate.POS.repository.Order.Order;
import com.facultate.POS.repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public List<Order> getAllOrders() {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return orderRepository.findAll();
    }


    public Order createOrder(List<Book> items) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        OrderDTO orderDTO = OrderDTO.builder()
                .status(OrderStatus.PENDING)
                .items(items)
                .build();

        return orderRepository.save(OrderAdapter.fromDTO(orderDTO));
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Service %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
