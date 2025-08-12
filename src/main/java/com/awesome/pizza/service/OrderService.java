package com.awesome.pizza.service;

import com.awesome.pizza.dto.CreateOrderRequest;
import com.awesome.pizza.dto.OrderResponse;
import com.awesome.pizza.dto.UpdateOrderRequest;
import com.awesome.pizza.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> listOrders(OrderStatus status, int limit);
    OrderResponse getOrderById(String id);
    OrderResponse updateOrder(String id, UpdateOrderRequest request);
}
