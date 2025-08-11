package com.awesome.pizza.controller;

import com.awesome.pizza.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Map<String, Order> orderStore = new ConcurrentHashMap<>();

    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        String id = UUID.randomUUID().toString();
        order.setId(id);
        orderStore.put(id, order);
        return order;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") String id) {
        return orderStore.get(id);
    }

    @GetMapping
    public List<Order> listOrders() {
        return new ArrayList<>(orderStore.values());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") String id) {
        orderStore.remove(id);
    }
}
