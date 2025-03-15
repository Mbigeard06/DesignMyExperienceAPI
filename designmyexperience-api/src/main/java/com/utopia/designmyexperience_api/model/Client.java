package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utopia.designmyexperience_api.model.enums.UserTypes;

import java.util.List;

/**
 * Represents a client in the system.
 * A client is a user who can book offerings (activities or services).
 * This class extends the base User class and includes a list of bookings made by the client.
 */
public class Client extends User {

    /**
     * List of bookings made by the client.
     */
    @JsonProperty("bookings")
    private List<Booking> bookings;

    public Client(){
        super();
    }

    /**
     * Constructs a Client object from basic user attributes (used when bookings are not yet loaded).
     *
     * @param id              The unique identifier of the user.
     * @param firstName       The first name of the client.
     * @param lastName        The last name of the client.
     * @param phone           The phone number of the client.
     * @param email           The email address of the client.
     * @param address         The postal address of the client.
     * @param userType        The type of user (should be CLIENT).
     * @param profilePicture  The profile picture of the client (as a byte array).
     */
    public Client(Long id, String firstName, String lastName,
                  String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        super(id, firstName, lastName, phone, email, address, userType, profilePicture);
    }

    /**
     * Constructs a Client object based on an existing User and a list of bookings.
     *
     * @param user     The base User object containing general user information.
     * @param bookings The list of bookings made by the client.
     */
    public Client(User user, List<Booking> bookings) {
        super(user.getId(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getEmail(), user.getAddress(),
                user.getUserType(), user.getProfilePicture());
        this.bookings = bookings;
    }

    /**
     * Gets the list of bookings made by the client.
     *
     * @return List of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings made by the client.
     *
     * @param bookings List of bookings.
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}