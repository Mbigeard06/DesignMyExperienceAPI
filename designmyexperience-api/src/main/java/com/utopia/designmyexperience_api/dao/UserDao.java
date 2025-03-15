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

    /**
     * Inserts a new user into the 'users' table in the database.
     *
     * @param user The User object to be created (must contain all fields except the ID, which is auto-generated).
     * @return The generated user ID if insertion is successful; otherwise -1.
     */
    public int createUser(User user, String hashedPassword) {
        // SQL insert statement with RETURNING clause to get the generated ID
        String sql = "INSERT INTO users (firstName, lastName, phone, email,  hashedpwd, address, userType, profilePicture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = databaseConnection.getDbConnection();              // Establish DB connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {               // Prepare SQL statement

            // Set parameters from the User object
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, hashedPassword);
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getUserType().name()); // Enum value as string
            stmt.setBytes(8, user.getProfilePicture());

            // Execute the query and get the generated ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Return the generated user ID
            }

        } catch (SQLException e) {
            // Log and propagate the error
            e.printStackTrace();
            throw new RuntimeException("Error creating user", e);
        }

        // If insertion failed
        return -1;
    }

    /**
     * Creates a business owner in the database.
     * First inserts the user into the 'users' table, then inserts business owner details into 'businessowners' table.
     *
     * @param businessOwner The BusinessOwner object to be created.
     * @return The generated user ID associated with the new business owner.
     */
    @Override
    public int createBusinessOwner(BusinessOwner businessOwner, String hasedPassword) {
        int userId = createUser(businessOwner, hasedPassword);

        if (userId == -1) {
            throw new RuntimeException("Failed to create base user for business owner.");
        }

        // Step 2: Insert business-specific details
        String sql = "INSERT INTO businessowners (id, businessname, businessaddress, businessdescription) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = databaseConnection.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, businessOwner.getBusinessName());
            stmt.setString(3, businessOwner.getBusinessAddress());
            stmt.setString(4, businessOwner.getBusinessDescription());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating business owner details for user ID: " + userId, e);
        }

        return userId;
    }

    /**
     * Creates a client in the database.
     * Simply inserts the user into the 'users' table with userType = CLIENT.
     *
     * @param client The Client object to be created.
     * @return The generated user ID associated with the client.
     */
    @Override
    public int createClient(Client client, String hasedPassword) {

        // Reuse the createUser() method
        int userId = createUser(client, hasedPassword);

        if (userId == -1) {
            throw new RuntimeException("Failed to create client user.");
        }

        return userId;
    }
}