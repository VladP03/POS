package com.facultate.POS.service;

import com.facultate.POS.model.Adapter.OrderAdapter;
import com.facultate.POS.model.Book;
import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.model.OrderStatus;
import com.facultate.POS.repository.Order.OrderRepository;
import com.facultate.POS.service.exception.NotFound.CollectionNotFoundException;
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

        setCollectionName(clientId.toString());
        checkIfCollectionExists();

        return OrderAdapter.toListDTO(orderRepository.findAll());
    }


    public OrderDTO createOrder(List<Book> items, Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        setCollectionName(clientId.toString());

        OrderDTO orderDTO = OrderDTO.builder()
                .status(OrderStatus.PENDING)
                .items(items)
                .build();

        return OrderAdapter.toDTO(orderRepository.save(OrderAdapter.fromDTO(orderDTO)));
    }



    private void setCollectionName(String clientId) {
        orderRepository.setCollectionName(String.format("client.%s", clientId));
    }


    private void checkIfCollectionExists() {
        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            String clientId =
                    orderRepository.getCollectionName().substring(orderRepository.getCollectionName().lastIndexOf(".") + 1);

            throw new CollectionNotFoundException(clientId);
        }
    }

    /**
     * Custom logger message
     * @param methodName the method name
     */
    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Service %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
