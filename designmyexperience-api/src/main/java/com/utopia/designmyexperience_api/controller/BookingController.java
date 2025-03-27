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
     *
     * @param offeringId
     * @param clientId
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam("offeringId") int offeringId,
                                           @RequestParam("clientId") int clientId) {
        try {
            int bookingId = bookingService.createBooking(offeringId, clientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("bookingId", bookingId));
        } catch (RuntimeException e) {
            // Si c'est une erreur métier comme "plus de capacité"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Toutes les autres erreurs
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create booking", "details", e.getMessage()));
        }
    }
}