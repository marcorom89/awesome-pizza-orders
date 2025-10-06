package com.awesome.pizza.dto;

import java.util.Optional;
import jakarta.validation.constraints.Size;
import com.awesome.pizza.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.Nulls;

public class UpdateOrderRequest {

    // ---- campi come Optional ----
    private Optional<OrderStatus> status = Optional.empty();
    private Optional<String> customerName = Optional.empty();
    private Optional<String> customerEmail = Optional.empty();
    private Optional<String> notes = Optional.empty();

    // ---- flag di presenza (non serializzati) ----
    @JsonIgnore
    private boolean statusPresent;
    @JsonIgnore
    private boolean customerNamePresent;
    @JsonIgnore
    private boolean customerEmailPresent;
    @JsonIgnore
    private boolean notesPresent;

    // ---- setter speciali: vengono invocati SOLO se la proprietà è presente nel
    // JSON ----
    // @JsonSetter(value = "status", nulls = Nulls.AS_EMPTY)
    @JsonSetter("status")
    public void setStatus(OrderStatus status) {
        this.status = Optional.ofNullable(status); // se JSON ha "status": null -> Optional.empty()
        this.statusPresent = true;
    }

    @JsonSetter("customerName")
    public void setCustomerName(@Size(max = 100) String customerName) {
        this.customerName = Optional.ofNullable(customerName);
        this.customerNamePresent = true;
    }

    @JsonSetter("customerEmail")
    public void setCustomerEmail(@Size(max = 200) String customerEmail) {
        this.customerEmail = Optional.ofNullable(customerEmail);
        this.customerEmailPresent = true;
    }

    @JsonSetter("notes")
    public void setNotes(String notes) {
        this.notes = Optional.ofNullable(notes);
        this.notesPresent = true;
    }

    // ---- getter (Optional) ----
    public Optional<OrderStatus> getStatus() {
        return status;
    }

    public Optional<String> getCustomerName() {
        return customerName;
    }

    public Optional<String> getCustomerEmail() {
        return customerEmail;
    }

    public Optional<String> getNotes() {
        return notes;
    }

    // ---- getter flag di presenza ----
    public boolean isStatusPresent() {
        return statusPresent;
    }

    public boolean isCustomerNamePresent() {
        return customerNamePresent;
    }

    public boolean isCustomerEmailPresent() {
        return customerEmailPresent;
    }

    public boolean isNotesPresent() {
        return notesPresent;
    }
}
