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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final RestTemplate restTemplate = new RestTemplate();


    public List<OrderDTO> getAllOrdersForClientId(Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        setCollectionName(clientId.toString());
        checkIfCollectionExists();

        return OrderAdapter.toListDTO(orderRepository.findAll());
    }


    @Transactional
    public OrderDTO createOrder(List<Book> items, Long clientId, String token) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        setCollectionName(clientId.toString());

        for (Book book : items) {
            URI bookUri = createURI(String.format("http://Book-app:8080/api/bookcollection/book/%s", book.getIsbn()));
            Object bookFromRequest = getBook(bookUri, token);

            // serialize data into JSON
            JSONObject bookJson = putIntoJSON(bookFromRequest);

            // check quantity
            int availableStock = bookJson.getInt("stock");

            if (availableStock >= book.getQuantity()) {
                // new stock
                int newStock = availableStock - book.getQuantity();
                changeStockForBook(newStock, bookJson, bookUri, token);
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

    private Object getBook(URI uri, String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> book = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Object.class);

        return book.getBody();
    }


    private JSONObject putIntoJSON(Object object) {
        return new JSONObject(new Gson().toJson(object));
    }


    private void changeStockForBook(int newStock, JSONObject bookJson, URI uri, String token) {
        HttpHeaders headers = new HttpHeaders();
        JSONObject newBookJson = new JSONObject();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));

        newBookJson.put("title", bookJson.getString("title"));
        newBookJson.put("publisher", bookJson.getString("publisher"));
        newBookJson.put("year", bookJson.getInt("year"));
        newBookJson.put("genre", bookJson.getString("genre"));
        newBookJson.put("price", bookJson.getDouble("price"));
        newBookJson.put("stock", newStock);

        // edit book with new available stock
        HttpEntity<String> httpEntity = new HttpEntity<>(newBookJson.toString(), headers);

        restTemplate.put(uri, httpEntity);
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
