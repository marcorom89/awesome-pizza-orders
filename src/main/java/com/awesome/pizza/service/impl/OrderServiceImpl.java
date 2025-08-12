package com.awesome.pizza.service.impl;

import com.awesome.pizza.dto.CreateOrderRequest;
import com.awesome.pizza.dto.OrderResponse;
import com.awesome.pizza.dto.UpdateOrderRequest;
import com.awesome.pizza.model.Order;
import com.awesome.pizza.model.OrderStatus;
import com.awesome.pizza.repository.OrderRepository;
import com.awesome.pizza.service.OrderService;
import com.awesome.pizza.web.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(order.getCreatedAt());
        order.setStatus(OrderStatus.PENDING);
        order.setItems(request.getItems());
        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setNotes(request.getNotes());
        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    @Override
    public List<OrderResponse> listOrders(OrderStatus status, int limit) {
        return orderRepository.list("Order", status, limit)
                .stream().map(OrderResponse::from).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderResponse.from(order);
    }

    @Override
    public OrderResponse updateOrder(String id, UpdateOrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (request.getStatus() != null) order.setStatus(request.getStatus());
        if (request.getCustomerName() != null) order.setCustomerName(request.getCustomerName());
        if (request.getCustomerEmail() != null) order.setCustomerEmail(request.getCustomerEmail());
        if (request.getNotes() != null) order.setNotes(request.getNotes());

        order.setUpdatedAt(Instant.now());
        orderRepository.save(order);
        return OrderResponse.from(order);
    }
}
