package com.facultate.POS.controller;

import com.facultate.POS.model.withoutPK.OrderWithoutPK;
import com.facultate.POS.repository.Order.Order;
import com.facultate.POS.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderWithoutPK orderWithoutPK) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(orderWithoutPK));
    }
}
