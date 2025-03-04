package com.utopia.designmyexperience_api.model;

import java.util.List;

/**
 * Represent a client in the system. A client can book offerings.
 */
public class Client extends User {
    private List<Booking> bookings;
}
