package com.awesome.pizza.repository;

import com.awesome.pizza.model.Order;
import com.awesome.pizza.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(String id);
    List<Order> list(String entityType, OrderStatus status, int limit);
}
