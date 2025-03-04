package com.utopia.designmyexperience_api.model;

/**
 * Represent an application user. Every user is wether a business owner or a client
 */
public class User {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String hashedPwd;
    private String address;
    private String userType;
    private byte[] profilePicture;
}
