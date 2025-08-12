package com.awesome.pizza.dto;

import jakarta.validation.constraints.Size;
import com.awesome.pizza.model.OrderStatus;

public class UpdateOrderRequest {
    private OrderStatus status;

    @Size(max = 100)
    private String customerName;

    @Size(max = 200)
    private String customerEmail;

    private String notes;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
