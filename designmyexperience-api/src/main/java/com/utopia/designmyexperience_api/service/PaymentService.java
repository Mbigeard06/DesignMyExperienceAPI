package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dto.ValidateBookingRequest;
import com.utopia.designmyexperience_api.validator.PaymentValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    private final BookingService bookingService;
    private PaymentValidator paymentValidator;

    public PaymentService(BookingService bookingService) {
        // Replace with your own Infura/Alchemy endpoint or local node
        this.bookingService = bookingService;
        this.paymentValidator = new PaymentValidator();
    }

    /**
     * Validates an Ethereum transaction by its hash after creating a booking.
     * If the transaction is invalid, the booking is deleted.
     *
     * @param validateBookingRequest Booking and transaction hash
     * @return true if the transaction is valid and booking is confirmed, false otherwise
     */
    public boolean validateBookingWithTransaction(ValidateBookingRequest validateBookingRequest) {
        int bookingId = -1;

        try {
            // Parse date string into LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime bookingDate = LocalDateTime.parse(validateBookingRequest.getBookingDateTime(), formatter);

            // Step 1: Create the booking and get its ID
            bookingId = bookingService.createBooking(
                    validateBookingRequest.getOfferingId(),
                    validateBookingRequest.getClientId(),
                    validateBookingRequest.getAttendeeCount(),
                    bookingDate
            );

            // Step 2: Validate Ethereum payment
            boolean isValid = paymentValidator.validatePayment(
                    validateBookingRequest.getTransactionHash(),
                    validateBookingRequest.getPrice()
            );

            // Step 3: If payment is invalid, delete the booking
            if (!isValid) {
                bookingService.deleteBooking(bookingId);
                throw new RuntimeException("Payment could not be validated. Booking has been cancelled.");
            }

            return true;

        } catch (Exception e) {
            // Cleanup: remove booking if it was created
            if (bookingId != -1) {
                bookingService.deleteBooking(bookingId);
            }
            throw new RuntimeException("Error during booking or payment validation: " + e.getMessage(), e);
        }
    }
}