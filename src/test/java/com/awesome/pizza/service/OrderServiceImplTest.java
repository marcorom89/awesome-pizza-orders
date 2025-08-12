package com.awesome.pizza.service;

import com.awesome.pizza.dto.CreateOrderRequest;
import com.awesome.pizza.dto.OrderResponse;
import com.awesome.pizza.dto.UpdateOrderRequest;
import com.awesome.pizza.model.Order;
import com.awesome.pizza.model.OrderItem;
import com.awesome.pizza.model.OrderStatus;
import com.awesome.pizza.repository.OrderRepository;
import com.awesome.pizza.service.impl.OrderServiceImpl;
import com.awesome.pizza.web.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    private OrderRepository repo;
    private OrderService service;

    @BeforeEach
    void setup() {
        repo = mock(OrderRepository.class);
        service = new OrderServiceImpl(repo);
    }

    @Test
    void createOrder_ok() {
        CreateOrderRequest r = new CreateOrderRequest();
        OrderItem item = new OrderItem();
        item.setName("Margherita");
        item.setProductCode("MARG");
        item.setQuantity(2);
        r.setItems(List.of(item));
        OrderResponse resp = service.createOrder(r);
        assertNotNull(resp.getId());
        verify(repo, times(1)).save(any(Order.class));
    }

    @Test
    void getOrder_notFound() {
        when(repo.findById("X")).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> service.getOrderById("X"));
    }

    @Test
    void updateOrder_status() {
        Order o = new Order();
        o.setId("1");
        o.setStatus(OrderStatus.PENDING);
        when(repo.findById("1")).thenReturn(Optional.of(o));

        UpdateOrderRequest u = new UpdateOrderRequest();
        u.setStatus(OrderStatus.IN_PROGRESS);
        OrderResponse resp = service.updateOrder("1", u);

        assertEquals(OrderStatus.IN_PROGRESS, resp.getStatus());
        verify(repo, times(1)).save(any(Order.class));
    }
}
