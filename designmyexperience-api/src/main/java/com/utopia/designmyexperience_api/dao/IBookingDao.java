package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.model.Booking;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing bookings in the system.
 * Provides methods to interact with the bookings database table.
 */
public interface IBookingDao {

    /**
     * Retrieves all bookings associated with a specific client.
     *
     * @param clientId The ID of the client.
     * @return A list of bookings made by the client.
     */
    List<Booking> getBookings(int clientId);

    /**
     *
     * @param offeringId offering id to be booked
     * @param client_id client id
     * @return booki
     */
    int setBooking(int offeringId, int client_id, int attendeeCount);

    /**
     * Retrieves all bookings associated with a specific offering.
     *
     * @param id offering id for which bookings are to be fetched.
     * @return A list of bookings related to the given offering.
     */
    List<Booking> getBookingOffering(int id);

}