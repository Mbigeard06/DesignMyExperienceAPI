package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.config.DatabaseConnection;
import com.utopia.designmyexperience_api.model.Booking;
import com.utopia.designmyexperience_api.service.OfferingService;
import com.utopia.designmyexperience_api.service.UserService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingDao implements IBookingDao {

    private final DatabaseConnection databaseConnection;
    private final UserService userService;
    private final OfferingService offeringService;

    public BookingDao(DatabaseConnection databaseConnection, UserService userService, OfferingService offeringService) {
        this.databaseConnection = databaseConnection;
        this.userService = userService;
        this.offeringService = offeringService;
    }

    @Override
    public List<Booking> getBookings(int clientId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE client_id = ?";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(buildBookingFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bookings for client ID: " + clientId, e);
        }

        return bookings;
    }

    @Override
    public int setBooking(int offeringId, int clientId, int attendeeCount, LocalDateTime bookingDate) {
        String sql = "INSERT INTO bookings (offering_id, client_id, bookingdate, attendee_count) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, offeringId);
            stmt.setInt(2, clientId);
            stmt.setTimestamp(3, Timestamp.valueOf(bookingDate));
            stmt.setInt(4, attendeeCount);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating booking", e);
        }

        return -1;
    }

    @Override
    public List<Booking> getBookingOffering(int id) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE offering_id = ?";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(buildBookingFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bookings for offering ID: " + id, e);
        }

        return bookings;
    }

    public int getRemainingCapacity(int offeringId) {
        String capacitySql = "SELECT capacity FROM offerings WHERE id = ?";
        String countSql = "SELECT COALESCE(SUM(attendee_count), 0) AS booked FROM bookings WHERE offering_id = ?";

        try (Connection conn = databaseConnection.getDbConnection()) {
            int capacity = 0;
            int booked = 0;

            try (PreparedStatement stmt = conn.prepareStatement(capacitySql)) {
                stmt.setInt(1, offeringId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    capacity = rs.getInt("capacity");
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(countSql)) {
                stmt.setInt(1, offeringId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    booked = rs.getInt("booked");
                }
            }

            return capacity - booked;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking remaining capacity for offering ID: " + offeringId, e);
        }
    }

    @Override
    public int checkDiscount(String discountCode, int offeringId) {
        String sql = """
        SELECT amount
        FROM discounts
        WHERE code = ?
          AND offering_id = ?
          AND CURRENT_DATE BETWEEN startingdate AND endingdate
    """;

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, discountCode);
            stmt.setInt(2, offeringId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("amount").intValue(); // Convert 10.00 → 10
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking discount for offering ID " + offeringId + " and code " + discountCode, e);
        }

        return 0; // No valid discount
    }

    @Override
    public int getNumberOfAttendeesAtTime(int serviceId, LocalDateTime date) {
        String sql = """
        SELECT COALESCE(SUM(attendee_count), 0) AS booked
        FROM bookings
        WHERE offering_id = ?
        AND DATE_TRUNC('hour', bookingdate) = DATE_TRUNC('hour', ?::timestamp)
    """;

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.setTimestamp(2, Timestamp.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("booked");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking number of attendees for service " + serviceId + " at " + date, e);
        }

        return 0;
    }


    /**
     * Helper method to build a Booking object from ResultSet.
     *
     * @param rs result set
     * @return Booking object
     * @throws SQLException if result set parsing fails
     */
    private Booking buildBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getLong("id"));
        booking.setBookingDate(rs.getTimestamp("bookingdate").toLocalDateTime());
        booking.setClient(userService.getClient(rs.getInt("client_id")));
        booking.setOffering(offeringService.getOffering(rs.getInt("offering_id")));
        booking.setAttendeeCount(rs.getInt("attendee_count"));
        return booking;
    }
}