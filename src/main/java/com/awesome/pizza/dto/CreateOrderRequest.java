package com.awesome.pizza.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import com.awesome.pizza.model.OrderItem;

public class CreateOrderRequest {
    @NotEmpty(message = "items cannot be empty")
    private List<OrderItem> items;

    @Size(max = 100)
    private String customerName;

    @Size(max = 200)
    private String customerEmail;

    private String notes;

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
