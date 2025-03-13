package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.config.DatabaseConnection;
import com.utopia.designmyexperience_api.model.BusinessOwner;
import com.utopia.designmyexperience_api.model.Client;
import com.utopia.designmyexperience_api.model.User;
import com.utopia.designmyexperience_api.model.enums.UserTypes;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao implements IUserDao {
    private final DatabaseConnection databaseConnection;

    public UserDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     *
     * @param id user id
     * @return user
     */
    @Override
    public User getUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("userName"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        UserTypes.valueOf(rs.getString("userType")), // Assumes enum mapping is correct
                        rs.getBytes("profilePicture")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving user with ID: " + id, e);
        }
        return null;
    }

    /**
     * Fetch all users from the database.
     * @return List of users.
     */
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("userName"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        UserTypes.valueOf(rs.getString("userType")),
                        rs.getBytes("profilePicture")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving users", e);
        }
        return users;
    }

    @Override
    public BusinessOwner getBusinessOwner(int id) {
        // Get the user first
        User user = getUser(id);

        if (user == null || user.getUserType() != UserTypes.BusinessOwner) {
            return null; // The user is not a businnes owner
        }

        String sql = "SELECT * FROM businessowners WHERE id = ?";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String businessName = rs.getString("businessname");
                String businessAddress = rs.getString("businessaddress");
                String businessDescription = rs.getString("businessdescription");

                return new BusinessOwner(user, businessName, businessAddress, businessDescription, null); // null = offerings
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving business owner with ID: " + id, e);
        }

        return null;
    }

    @Override
    public Client getClient(int id) {
        // Get the user first
        User user = getUser(id);

        if (user == null || user.getUserType() != UserTypes.Client) {
            return null; // The user is not a businnes owner
        }

        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Client(user,null); // null = offerings
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving client with ID: " + id, e);
        }

        return null;
    }
}