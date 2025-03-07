package com.utopia.designmyexperience_api.model;

/**
 * Represent a booking made by a client for a specific offering.
 */
public class Booking {
    private Long id;
    private Client client;
    private Offering offering;
    private String status;
    private java.util.Date bookingDate;
}