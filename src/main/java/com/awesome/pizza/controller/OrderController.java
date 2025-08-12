package com.awesome.pizza.controller;

import com.awesome.pizza.dto.CreateOrderRequest;
import com.awesome.pizza.dto.OrderResponse;
import com.awesome.pizza.dto.UpdateOrderRequest;
import com.awesome.pizza.model.OrderStatus;
import com.awesome.pizza.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @GetMapping
    public List<OrderResponse> list(@RequestParam(name = "status", required = false) OrderStatus status,
            @RequestParam(name = "limit", defaultValue = "20") int limit) {
        return orderService.listOrders(status, Math.max(1, Math.min(100, limit)));
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable("id") String id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping("/{id}")
    public OrderResponse update(@PathVariable("id") String id,
            @RequestBody @Valid UpdateOrderRequest request) {
        return orderService.updateOrder(id, request);
    }
}
