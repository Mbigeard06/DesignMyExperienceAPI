package com.utopia.designmyexperience_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.utopia.designmyexperience_api.model.enums.UserTypes;

/**
 * Represents an application user. Every user is either a business owner or a client.
 */
public class User {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("userType")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UserTypes userType;

    @JsonProperty("profilePicture")
    private byte[] profilePicture;

    public User() {
        // Empty constructor required by Jackson
    }

    public User(Long id, String firstName, String lastName,
                String phone, String email, String address, UserTypes userType, byte[] profilePicture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.userType = userType;
        this.profilePicture = profilePicture;
    }


    // Getters and Setters (Recommandé pour la sérialisation)
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public UserTypes getUserType() { return userType; }
    public byte[] getProfilePicture() { return profilePicture; }
}