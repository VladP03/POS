package com.facultate.POS.controller;

import com.facultate.POS.model.Book;
import com.facultate.POS.model.DTO.OrderDTO;
import com.facultate.POS.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Validated
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/orders/{ClientID}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersForClientId(@PathVariable("ClientID") Long clientId) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok(orderService.getAllOrdersForClientId(clientId));
    }


    @PostMapping("/order/{ClientID}")
    public ResponseEntity<OrderDTO> createOrder(
            @PathVariable("ClientID") Long clientId,
            @RequestBody @Valid List<Book> items) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(items, clientId));
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
