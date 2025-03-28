package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.config.DatabaseConnection;
import com.utopia.designmyexperience_api.model.Activity;
import com.utopia.designmyexperience_api.model.Offering;
import com.utopia.designmyexperience_api.model.Service;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;
import com.utopia.designmyexperience_api.service.UserService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfferingDao implements IOfferingDao {

    private final DatabaseConnection databaseConnection;
    private final UserService userService;

    public OfferingDao(DatabaseConnection databaseConnection, UserService userService) {
        this.databaseConnection = databaseConnection;
        this.userService = userService;
    }

    @Override
    public List<Offering> getOfferings(int id) {
        List<Offering> offerings = new ArrayList<>();
        String sql = "SELECT * FROM offerings WHERE businessowner_id = ?";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                offerings.add(buildBasicOfferingFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching offerings", e);
        }

        return offerings;
    }

    @Override
    public Offering getOffering(int id) {
        String sql = "SELECT * FROM offerings WHERE id = ?";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return buildBasicOfferingFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching offering with ID: " + id, e);
        }

        return null;
    }

    @Override
    public Activity getActivity(int id) {
        Offering base = getOffering(id);
        if (base == null) return null;

        String sql = "SELECT * FROM activities WHERE id = ?";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Activity(
                        base.getId(),
                        base.getTitle(),
                        base.getDescription(),
                        base.getCapacity(),
                        base.getLocation(),
                        base.getType(),
                        base.getPicture(),
                        base.getPrice(),
                        base.getBusinessOwner(),
                        base.getDuration(),
                        rs.getTimestamp("startdate").toLocalDateTime(),
                        rs.getTimestamp("enddate").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching activity with ID: " + id, e);
        }

        return null;
    }

    @Override
    public Service getService(int id) {
        Offering base = getOffering(id);
        if (base == null) return null;

        String sql = "SELECT * FROM services WHERE id = ?";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Service(
                        base.getId(),
                        base.getTitle(),
                        base.getDescription(),
                        base.getCapacity(),
                        base.getLocation(),
                        base.getType(),
                        base.getPicture(),
                        base.getPrice(),
                        base.getBusinessOwner(),
                        base.getDuration(),
                        rs.getTimestamp("opening").toLocalDateTime(),
                        rs.getTimestamp("closing").toLocalDateTime(),
                        rs.getBoolean("ondemand"),
                        rs.getString("servicearea")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching service with ID: " + id, e);
        }

        return null;
    }

    private Offering buildBasicOfferingFromResultSet(ResultSet rs) throws SQLException {
        Offering offering = new Offering();
        offering.setId(rs.getLong("id"));
        offering.setTitle(rs.getString("title"));
        offering.setDescription(rs.getString("description"));
        offering.setCapacity(rs.getInt("capacity"));
        offering.setLocation(rs.getString("location"));
        offering.setType(OfferingTypes.valueOf(rs.getString("type")));
        offering.setPicture(rs.getBytes("picture"));
        offering.setPrice(rs.getDouble("price"));
        offering.setDuration(rs.getDouble("duration"));
        offering.setBusinessOwner(userService.getBusinessOwner(rs.getInt("businessowner_id")));
        return offering;
    }

    @Override
    public List<Offering> getOfferings() {
        List<Offering> offerings = new ArrayList<>();
        String sql = "SELECT * FROM offerings";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Offering offering = buildBasicOfferingFromResultSet(rs);
                offerings.add(offering);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving offerings", e);
        }

        return offerings;
    }

    /**
     * Creates a generic offering in the database.
     * @param offering The base offering data to insert.
     * @return The generated ID of the created offering.
     */
    public long createOffering(Offering offering) {
        String sql = "INSERT INTO offerings (title, description, capacity, location, type, picture, price, duration, businessowner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, offering.getTitle());
            stmt.setString(2, offering.getDescription());
            stmt.setInt(3, offering.getCapacity());
            stmt.setString(4, offering.getLocation());
            stmt.setString(5, offering.getType().name());
            stmt.setBytes(6, offering.getPicture());
            stmt.setDouble(7, offering.getPrice());
            stmt.setDouble(8, offering.getDuration());
            stmt.setLong(9, offering.getBusinessOwner().getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating offering", e);
        }

        return -1;
    }

    /**
     * Creates an activity by first creating a base offering and then adding activity-specific fields.
     * @param activity The Activity object to be stored.
     * @return The ID of the created activity (same as the offering ID).
     */
    @Override
    public int createActivity(Activity activity) {
        long offeringId = createOffering(activity);

        if (offeringId <= 0) {
            throw new RuntimeException("Failed to create offering for the activity.");
        }

        String sql = "INSERT INTO activities (id, startdate, enddate) VALUES (?, ?, ?)";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, offeringId);
            stmt.setTimestamp(2, Timestamp.valueOf(activity.getStartDate()));
            stmt.setTimestamp(3, Timestamp.valueOf(activity.getEndDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating activity details", e);
        }

        return (int) offeringId;
    }

    /**
     * Creates a service offering in the database.
     * First inserts a generic offering, then adds service-specific fields.
     *
     * @param service The Service object to insert.
     * @return The ID of the created service (same as the offering ID).
     */
    @Override
    public int createService(Service service) {
        long offeringId = createOffering(service);

        if (offeringId <= 0) {
            throw new RuntimeException("Failed to create offering for the service.");
        }

        String sql = "INSERT INTO services (id, opening, closing, ondemand, servicearea) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, offeringId);
            stmt.setTimestamp(2, Timestamp.valueOf(service.getOpening()));
            stmt.setTimestamp(3, Timestamp.valueOf(service.getClosing()));
            stmt.setBoolean(4, service.isOnDemand());
            stmt.setString(5, service.getServiceArea());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating service details", e);
        }

        return (int) offeringId;
    }

    /**
     * Retrieves all upcoming activities (startDate after current timestamp).
     * @return List of upcoming Activity offerings.
     */
    public List<Activity> getAllUpcomingActivities() {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM activities WHERE startdate > ?";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Activity activity = getActivity(id);
                if (activity != null) {
                    activities.add(activity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching upcoming activities", e);
        }
        return activities;
    }

    /**
     * Retrieves all services from the database.
     * @return List of all Service offerings.
     */
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Service service = getService(id);
                if (service != null) {
                    services.add(service);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all services", e);
        }

        return services;
    }

    /**
     * Returns the number of available spots for a given offering.
     *
     * @param offeringId the ID of the offering
     * @return number of spots left
     */
    public int getRemainingCapacity(int offeringId) {
        String capacitySql = "SELECT capacity FROM offerings WHERE id = ?";
        String countSql = "SELECT COUNT(*) AS booked FROM bookings WHERE offering_id = ?";

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

    /**
     * Retrieves all upcoming services (closing time after current timestamp).
     * @return List of upcoming Service offerings.
     */
    public List<Service> getAllUpcomingServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE closing > ?";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Service service = getService(id);
                if (service != null) {
                    services.add(service);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching upcoming services", e);
        }

        return services;
    }
}
