package com.awesome.pizza.dto;

import com.awesome.pizza.model.Order;
import com.awesome.pizza.model.OrderStatus;
import com.awesome.pizza.model.OrderItem;

import java.time.Instant;
import java.util.List;

public class OrderResponse {
    private String id;
    private Instant createdAt;
    private Instant updatedAt;
    private OrderStatus status;
    private String customerName;
    private String customerEmail;
    private String notes;
    private List<OrderItem> items;

    public static OrderResponse from(Order o) {
        OrderResponse r = new OrderResponse();
        r.id = o.getId();
        r.createdAt = o.getCreatedAt();
        r.updatedAt = o.getUpdatedAt();
        r.status = o.getStatus();
        r.customerName = o.getCustomerName();
        r.customerEmail = o.getCustomerEmail();
        r.notes = o.getNotes();
        r.items = o.getItems();
        return r;
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
