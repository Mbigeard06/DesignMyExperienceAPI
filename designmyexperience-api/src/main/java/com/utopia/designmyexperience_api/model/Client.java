package com.utopia.designmyexperience_api.model;

import com.utopia.designmyexperience_api.model.enums.UserTypes;

/**
 * Represent a client in the system. A client can book offerings.
 */
public class Client extends User {

    public Client(Long id, String userName, String firstName, String lastName, String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        super(id, userName, firstName, lastName, phone, email, address, userType, profilePicture);
    }
}
