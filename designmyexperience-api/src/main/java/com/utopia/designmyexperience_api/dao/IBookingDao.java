package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.model.Booking;

import java.time.LocalDateTime;
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
     * Creates a new booking for a specific offering and client
     *
     * @param offeringId     ID of the offering to be booked.
     * @param clientId       ID of the client making the booking.
     * @param attendeeCount  Number of people included in the booking.
     * @param bookingDate    Desired date and hour for the booking (must be on the hour).
     * @return the ID of the newly created booking.
     * @throws RuntimeException if no capacity is available or if the offering type is invalid.
     */
    int setBooking(int offeringId, int clientId, int attendeeCount, LocalDateTime bookingDate);

    /**
     * Retrieves all bookings associated with a specific offering.
     *
     * @param id offering id for which bookings are to be fetched.
     * @return A list of bookings related to the given offering.
     */
    List<Booking> getBookingOffering(int id);

    /**
     * Returns the number of attendees already booked for a specific service
     * at the exact minute (ignoring seconds and milliseconds).
     *
     * @param serviceId the ID of the service
     * @param date      the date/time of the booking (to the minute)
     * @return total number of attendees booked for that service at that date/time
     */
    public int getNumberOfAttendeesAtTime(int serviceId, LocalDateTime date);

    /**
     * Returns the number of available spots for a given offering.
     *
     * @param offeringId the ID of the offering
     * @return number of spots left
     */
    public int getRemainingCapacity(int offeringId);

    /**
     *
     * @param discount discode count
     * @param offeringId id of the offering to be discounted
     * @return percentage of discount
     */
    public int checkDiscount(String discount, int offeringId);

}