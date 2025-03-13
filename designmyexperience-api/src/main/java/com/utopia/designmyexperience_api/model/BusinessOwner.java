package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents a business owner. A business owner provides offerings.
 */
public class BusinessOwner extends User {

    @JsonProperty("businessName")
    private String businessName;

    @JsonProperty("businessAddress")
    private String businessAddress;

    @JsonProperty("businessDescription")
    private String businessDescription;

    @JsonProperty("offerings")
    private List<Offering> offerings;

    /**
     * Constructs a BusinessOwner object based on an existing User and additional business-related details.
     *
     * @param user                The base User object containing general user information.
     * @param businessName        The name of the business owned by the user.
     * @param businessAddress     The address of the business.
     * @param businessDescription A short description of the business.
     * @param offerings           The list of offerings (activities or services) provided by the business owner.
     */
    public BusinessOwner(User user, String businessName, String businessAddress, String businessDescription, List<Offering> offerings) {
        super(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getEmail(), user.getAddress(),
                user.getUserType(), user.getProfilePicture());
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessDescription = businessDescription;
        this.offerings = offerings;
    }

    /**
     * Sets the list of offerings proposed by the business owner.
     *
     * @param offerings List of offerings.
     */
    public void setActivites(List<Offering> offerings) {
        this.offerings = offerings;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }
}