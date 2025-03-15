package com.utopia.designmyexperience_api.model;

/**
 * Represent an activity that extends an offering.
 */
public class Activity extends Offering {
    private long id;
    private String activityType;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private Boolean equipementProvided;
}
