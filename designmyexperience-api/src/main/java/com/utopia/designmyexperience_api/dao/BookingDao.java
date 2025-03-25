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

    /**
     * Retrieves all bookings for a specific client.
     *
     * @param clientId the ID of the client
     * @return List of Booking objects
     */
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

    /**
     * Creates a new booking for a given offering.
     *
     * @param  offeringId the offering to be booked
     * @return the generated booking ID
     */
    @Override
    public int setBooking(int offeringId, int client_id) {
        String sql = "INSERT INTO bookings (offering_id, client_id, bookingdate) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, offeringId);
            stmt.setLong(2, client_id); // Assuming business owner acts as client here
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

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

    /**
     * Retrieves all bookings associated with a specific offering.
     *
     * @param id of the offering object
     * @return list of bookings associated to the offering
     */
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
        return booking;
    }
}
