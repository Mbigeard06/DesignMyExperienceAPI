package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.dto.ValidateBookingRequest;
import com.utopia.designmyexperience_api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Validate a booking with an Ethereum transaction hash.
     * First inserts the booking, then verifies the transaction.
     * If invalid, deletes the booking.
     *
     * @param requestDto DTO containing the booking and transaction hash
     * @return ResponseEntity with validation result
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateBooking(@RequestBody ValidateBookingRequest requestDto) {
        try {
            boolean isValid = paymentService.validateBookingWithTransaction(requestDto);

            if (isValid) {
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "message", "Booking confirmed and payment validated."
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "status", "fail",
                        "message", "Transaction invalid or insufficient."
                ));
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "fail",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "An error occurred while validating the booking.",
                    "details", e.getMessage()
            ));
        }
    }
}