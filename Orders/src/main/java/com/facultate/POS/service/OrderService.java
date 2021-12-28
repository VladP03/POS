package com.facultate.POS.service;

import com.facultate.POS.model.Adapter.OrderAdapter;
import com.facultate.POS.model.Book;
import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.model.OrderStatus;
import com.facultate.POS.repository.Order.OrderRepository;
import com.facultate.POS.service.exception.InsufficientStockException;
import com.facultate.POS.service.exception.NotFound.CollectionNotFoundException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;
    private RestTemplate restTemplate;


    public List<OrderDTO> getAllOrdersForClientId(Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        setCollectionName(clientId.toString());
        checkIfCollectionExists();

        return OrderAdapter.toListDTO(orderRepository.findAll());
    }


    @Transactional
    public OrderDTO createOrder(List<Book> items, Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        restTemplate = new RestTemplate();
        setCollectionName(clientId.toString());

        for (Book book : items) {
            URI uri = createURI(String.format("http://localhost:8080/api/bookcollection/book/%s", book.getIsbn()));

            // get data from URI
            Object object = restTemplate.getForObject(uri, Object.class);
            // serialize data into JSON
            JSONObject jsonObject = putIntoJSON(object);

            // check quantity
            int availableStock = jsonObject.getInt("stock");

            if (availableStock >= book.getQuantity()) {
                // new stock
                int newStock = availableStock - book.getQuantity();

                JSONObject newJsonObject = new JSONObject();

                newJsonObject.put("title", jsonObject.getString("title"));
                newJsonObject.put("publisher", jsonObject.getString("publisher"));
                newJsonObject.put("year", jsonObject.getInt("year"));
                newJsonObject.put("genre", jsonObject.getString("genre"));
                newJsonObject.put("price", jsonObject.getDouble("price"));
                newJsonObject.put("stock", newStock);

                // edit book with new available stock
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> httpEntity = new HttpEntity<>(newJsonObject.toString(), headers);

                restTemplate.put(uri, httpEntity);
            } else {
                // not enough stock
                throw new InsufficientStockException(book.getIsbn());
            }
        }

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
                    orderRepository.getCollectionName()
                            .substring(orderRepository.getCollectionName().lastIndexOf(".") + 1);

            throw new CollectionNotFoundException(clientId);
        }
    }


    private URI createURI(String uriAsString) {
        try {
            return new URI(uriAsString);
        } catch (URISyntaxException exception) {
            throw new RuntimeException("Invalid URI");
        }
    }

    private JSONObject putIntoJSON(Object object) {
        return new JSONObject(new Gson().toJson(object));
    }

    /**
     * Custom logger message
     *
     * @param methodName the method name
     */
    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Service %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
