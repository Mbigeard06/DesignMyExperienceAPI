package com.utopia.designmyexperience_api.model;

import java.util.List;

/**
 * Represent an offering that can be booked by clients. It is provided by a business owner.
 */
public class Offering {
    private Long id;
    private String name;
    private String description;
    private double price;
    private BusinessOwner businessOwner;
    private List<Booking> bookings;

    /**
     *
     * @return Offering ID
     */
    public Long getOfferingId(){
        return this.id;
    }
}