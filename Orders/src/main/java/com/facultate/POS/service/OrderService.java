package com.facultate.POS.service;

import com.facultate.POS.model.Adapter.OrderAdapter;
import com.facultate.POS.model.Book;
import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.model.OrderStatus;
import com.facultate.POS.repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;


    public List<OrderDTO> getAllOrdersForClientId(Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        orderRepository.setCollectionName(String.format("client.%s", clientId.toString()));

        if (mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            return OrderAdapter.toListDTO(orderRepository.findAll());
        } else {
            throw new RuntimeException();
        }
    }


    public OrderDTO createOrder(List<Book> items, Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        orderRepository.setCollectionName(String.format("client.%s", clientId.toString()));
        OrderDTO orderDTO = OrderDTO.builder()
                .status(OrderStatus.PENDING)
                .items(items)
                .build();

        return OrderAdapter.toDTO(orderRepository.save(OrderAdapter.fromDTO(orderDTO)));
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Service %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
