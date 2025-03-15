package com.utopia.designmyexperience_api.dto;

import com.utopia.designmyexperience_api.model.BusinessOwner;

public class CreateBusinessOwnerRequest {

    private BusinessOwner businessOwner;
    private String password;

    public BusinessOwner getBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}