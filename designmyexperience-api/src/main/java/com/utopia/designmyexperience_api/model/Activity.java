package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;
import java.util.Date;

/**
 * Represent an activity that extends an offering.
 */
public class Activity extends Offering {

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("endDate")
    private Date endDate;

    @JsonProperty("equipementProvided")
    private Boolean equipementProvided;

    // Constructor
    public Activity(Long id, String title, String description, int capacity, String location,
                    OfferingTypes type, byte[] picture, double price, BusinessOwner businessOwner,
                    double duration, Date startDate, Date endDate, Boolean equipementProvided) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getEquipementProvided() {
        return equipementProvided;
    }

    public void setEquipementProvided(Boolean equipementProvided) {
        this.equipementProvided = equipementProvided;
    }
}
