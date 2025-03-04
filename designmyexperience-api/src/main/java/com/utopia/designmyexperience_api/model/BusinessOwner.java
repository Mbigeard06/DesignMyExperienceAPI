package com.utopia.designmyexperience_api.model;

import java.util.List;

/**
 * Represent a business owner. A business owner provides offerings.
 */
public class BusinessOwner extends User {
    private String businessName;
    private String businessAddress;
    private String businessDescription;
    private List<Offering> offerings;
}