package com.utopia.designmyexperience_api.model;

/**
 * Represent a service that extends an offering.
 */
public class Service extends Offering {
    private Long id;
    private String serviceType;
    private java.util.Date opening;
    private  java.util.Date closing;
    private boolean onDemand;
    private String serviceArea;
}
