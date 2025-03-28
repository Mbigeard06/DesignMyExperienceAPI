package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.model.Booking;
import com.utopia.designmyexperience_api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Get all bookings for a specific client.
     * @param clientId The client ID.
     * @return List of bookings.
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getBookingsByClient(@PathVariable int clientId) {
        try {
            List<Booking> bookings = bookingService.getBookingsByClient(clientId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching bookings", "details", e.getMessage()));
        }
    }

    /**
     * Get all bookings associated with a specific offering.
     * @param offeringId The offering ID.
     * @return List of bookings.
     */
    @GetMapping("/offering/{offeringId}")
    public ResponseEntity<?> getBookingsByOffering(@PathVariable int offeringId) {
        try {
            List<Booking> bookings = bookingService.getBookingsForOffering(offeringId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching bookings by offering", "details", e.getMessage()));
        }
    }

    /**
     * Create a booking for a client on a given offering, specifying number of attendees.
     *
     * @param offeringId   ID of the offering
     * @param clientId     ID of the client making the booking
     * @param attendeeCount Number of people included in the booking
     * @return Response with booking ID or error message
     */
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam("offeringId") int offeringId,
                                           @RequestParam("clientId") int clientId,
                                           @RequestParam("attendeeCount") int attendeeCount) {
        try {
            int bookingId = bookingService.createBooking(offeringId, clientId, attendeeCount);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("bookingId", bookingId));
        } catch (RuntimeException e) {
            // Business logic error, e.g. not enough capacity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Other unexpected errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create booking", "details", e.getMessage()));
        }
    }
}