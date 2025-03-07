package com.utopia.designmyexperience_api.model;

import com.utopia.designmyexperience_api.model.enums.UserTypes;

import java.util.List;

/**
 * Represent a business owner. A business owner provides offerings.
 */
public class BusinessOwner extends User {
    private Long id;
    private String businessName;
    private String businessAddress;
    private String businessDescription;
    private List<Offering> offerings;

    public BusinessOwner(Long id, String userName, String firstName, String lastName, String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        super(id, userName, firstName, lastName, phone, email, address, userType, profilePicture);
    }
}