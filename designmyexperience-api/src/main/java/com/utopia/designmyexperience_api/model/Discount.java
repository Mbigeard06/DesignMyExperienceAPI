package com.utopia.designmyexperience_api.model;

/**
 * Represent a discount applied to an offering.
 */
public class Discount {
    private Long id;
    private String code;
    private double amount;
    private java.util.Date startingDate;
    private java.util.Date endingDate;
    private Offering offering;
}