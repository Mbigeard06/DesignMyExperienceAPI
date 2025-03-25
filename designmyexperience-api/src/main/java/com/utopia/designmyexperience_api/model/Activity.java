package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;

import java.time.LocalDateTime;

/**
 * Represent an activity that extends an offering.
 */
public class Activity extends Offering {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonProperty("equipementProvided")
    private Boolean equipementProvided;

    // Constructor
    public Activity(Long id, String title, String description, int capacity, String location,
                    OfferingTypes type, byte[] picture, double price, BusinessOwner businessOwner,
                    double duration, LocalDateTime startDate, LocalDateTime endDate, Boolean equipementProvided) {
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
        this.startDate = startDate;
        this.endDate = endDate;
        this.equipementProvided = equipementProvided;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getEquipementProvided() {
        return equipementProvided;
    }

    public void setEquipementProvided(Boolean equipementProvided) {
        this.equipementProvided = equipementProvided;
    }
}
