package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dao.IBookingDao;
import com.utopia.designmyexperience_api.model.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final IBookingDao bookingDao;

    public BookingService(IBookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    /**
     * Retrieve all bookings for a specific client.
     * @param clientId the ID of the client
     * @return list of bookings
     */
    public List<Booking> getBookingsByClient(int clientId) {
        return bookingDao.getBookings(clientId);
    }

    /**
     * Retrieves all bookings associated with a specific offering.
     *
     * @param offeringId The ID of the offering for which to retrieve bookings.
     * @return A list of Booking objects associated with the specified offering.
     */
    public List<Booking> getBookingsForOffering(int offeringId) {
        return bookingDao.getBookingOffering(offeringId);
    }


    /**
     * Create a new booking for an offering by a specific client.
     * @param offeringId the offering to book
     * @param clientId the ID of the client booking it
     * @return booking ID
     */
    public int createBooking(int offeringId, int clientId) {
        return bookingDao.setBooking(offeringId, clientId);
    }
}
