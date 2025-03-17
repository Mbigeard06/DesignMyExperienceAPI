package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.config.DatabaseConnection;
import com.utopia.designmyexperience_api.model.Activity;
import com.utopia.designmyexperience_api.model.Offering;
import com.utopia.designmyexperience_api.model.Service;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfferingDao implements IOfferingDao {

    private final DatabaseConnection databaseConnection;

    public OfferingDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
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
                        rs.getDate("startdate"),
                        rs.getDate("enddate"),
                        rs.getBoolean("equipementprovided")
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
                        rs.getDate("opening"),
                        rs.getDate("closing"),
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
        return offering;
    }
}