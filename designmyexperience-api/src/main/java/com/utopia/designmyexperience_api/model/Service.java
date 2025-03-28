package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;
import java.time.LocalDateTime;

/**
 * Represent a service that extends an offering.
 */
public class Service extends Offering {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime opening;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime closing;

    @JsonProperty("onDemand")
    private boolean onDemand;

    @JsonProperty("serviceArea")
    private String serviceArea;

    // Constructor
    public Service(Long id, String title, String description, int capacity, String location,
                   OfferingTypes type, byte[] picture, double price, BusinessOwner businessOwner,
                   double duration, LocalDateTime opening, LocalDateTime closing, boolean onDemand, String serviceArea) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setCapacity(capacity);
        setLocation(location);
        setType(type);
        setPicture(picture);
        setPrice(price);
        setBusinessOwner(businessOwner);
        setDuration(duration);
        this.opening = opening;
        this.closing = closing;
        this.onDemand = onDemand;
        this.serviceArea = serviceArea;
    }

    public LocalDateTime getOpening() {
        return opening;
    }

    public void setOpening(LocalDateTime opening) {
        this.opening = opening;
    }

    public LocalDateTime getClosing() {
        return closing;
    }

    public void setClosing(LocalDateTime closing) {
        this.closing = closing;
    }

    public boolean isOnDemand() {
        return onDemand;
    }

    public void setOnDemand(boolean onDemand) {
        this.onDemand = onDemand;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }
}