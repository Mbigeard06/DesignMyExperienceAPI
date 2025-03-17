package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;

/**
 * Represent an offering that can be booked by clients. It is provided by a business owner.
 */
public class Offering {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("capacity")
    private int capacity;

    @JsonProperty("location")
    private String location;

    @JsonProperty("type")
    private OfferingTypes type;

    @JsonProperty("picture")
    private byte[] picture;

    @JsonProperty("price")
    private double price;

    @JsonProperty("businessOwner")
    private BusinessOwner businessOwner;

    @JsonProperty("duration")
    private double duration;

    /**
     * @return Offering ID
     */
    public Long getOfferingId() {
        return this.id;
    }

    // Optionally add getters and setters if needed for serialization/deserialization
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public OfferingTypes getType() { return type; }
    public void setType(OfferingTypes type) { this.type = type; }

    public byte[] getPicture() { return picture; }
    public void setPicture(byte[] picture) { this.picture = picture; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public BusinessOwner getBusinessOwner() { return businessOwner; }
    public void setBusinessOwner(BusinessOwner businessOwner) { this.businessOwner = businessOwner; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }
}