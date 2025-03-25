package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Booking {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("client")
    private Client client;

    @JsonProperty("offering")
    private Offering offering;

    @JsonProperty("bookingDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bookingDate;

    // Constructors
    public Booking() {
    }

    public Booking(Long id, Client client, Offering offering, LocalDateTime bookingDate) {
        this.id = id;
        this.client = client;
        this.offering = offering;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Offering getOffering() {
        return offering;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
}