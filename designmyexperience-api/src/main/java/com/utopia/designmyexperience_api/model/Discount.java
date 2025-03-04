package com.utopia.designmyexperience_api.model;

/**
 * Represent a discount applied to an offering.
 */
public class Discount {
    private Long id;
    private double percentage;
    private String description;
    private Offering offering;
}