package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dao.IBookingDao;
import com.utopia.designmyexperience_api.dao.IOfferingDao;
import com.utopia.designmyexperience_api.model.Booking;
import com.utopia.designmyexperience_api.model.Offering;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final IBookingDao bookingDao;
    private final IOfferingDao offeringDao;

    public BookingService(IBookingDao bookingDao, IOfferingDao offeringDao) {
        this.bookingDao = bookingDao;
        this.offeringDao = offeringDao;
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
     *
     * @param offeringId    the offering to book
     * @param clientId      the ID of the client booking it
     * @param attendeeCount number of people included in the booking
     * @param date          desired date and hour for the booking (must be on the hour).
     * @return booking ID
     */
    public int createBooking(int offeringId, int clientId, int attendeeCount, LocalDateTime date) {
        Offering offering = this.offeringDao.getOffering(offeringId);

        boolean hasCapacity = switch (offering.getType()) {
            case Activity -> bookingDao.getRemainingCapacity(offeringId) >= attendeeCount;
            case Service -> capacityLeft(offering, attendeeCount, date);
            default -> throw new RuntimeException("Unrecognized type of offering");
        };

        if (hasCapacity) {
            return bookingDao.setBooking(offeringId, clientId, attendeeCount, date);
        } else {
            throw new RuntimeException("No more capacity available for this offering.");
        }
    }

    /**
     * Checks if there is enough capacity left for a booking request at a given time.
     *
     * @param offering        the service offering to be booked
     * @param attendeeCount   number of people trying to book
     * @param bookingTime  the desired booking start time
     * @return true if there is enough capacity left, false otherwise
     */
    private boolean capacityLeft(Offering offering, int attendeeCount, LocalDateTime bookingTime) {
        int capacity = offering.getCapacity();
        double duration = offering.getDuration(); // ex: 2.0
        int durationInHours = (int) Math.ceil(duration);

        int totalAlreadyBooked = 0;

        // check hours that can overlap
        for (int i = -durationInHours + 1; i < durationInHours; i++) {
            LocalDateTime hourToCheck = bookingTime.plusHours(i);
            totalAlreadyBooked += bookingDao.getNumberOfAttendeesAtTime(offering.getId().intValue(), hourToCheck);
        }

        return totalAlreadyBooked + attendeeCount <= capacity;
    }
}
