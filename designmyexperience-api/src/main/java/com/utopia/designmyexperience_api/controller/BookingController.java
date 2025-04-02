package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.model.Booking;
import com.utopia.designmyexperience_api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * Checks whether a booking can be made for a given offering, client, attendee count, and date.
     *
     * @param offeringId      offering id
     * @param clientId        client id
     * @param attendeeCount   attendee count
     * @param bookingDateTime desired booking time in ISO_LOCAL_DATE_TIME format (e.g., 2025-04-04T14:00)
     * @return ResponseEntity with true if bookable, or an error if not
     */
    @PostMapping("/checkBooking")
    public ResponseEntity<?> checkBooking(@RequestParam("offeringId") int offeringId,
                                          @RequestParam("clientId") int clientId,
                                          @RequestParam("attendeeCount") int attendeeCount,
                                          @RequestParam("bookingDateTime") String bookingDateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime bookingDate = LocalDateTime.parse(bookingDateTime, formatter);

            boolean isBookable = bookingService.isBookable(offeringId, clientId, attendeeCount, bookingDate);
            if (!isBookable) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "The offering is not available at the selected time or there is not enough capacity."));
            }

            return ResponseEntity.ok(Map.of("isBookable", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request", "details", e.getMessage()));
        }
    }

    /**
     * Check if a discount code is valid for a specific offering and return the percentage.
     *
     * @param code Discount code (as request param)
     * @param offeringId ID of the offering to check against (as request param)
     * @return JSON containing the discount percentagea
     */
    @GetMapping("/check_discount")
    public ResponseEntity<?> checkDiscount(@RequestParam String code, @RequestParam int offeringId) {
        try {
            int discount = bookingService.checkDiscount(code, offeringId);
            return ResponseEntity.ok(Map.of("discount", discount));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to check discount", "details", e.getMessage()));
        }
    }

}