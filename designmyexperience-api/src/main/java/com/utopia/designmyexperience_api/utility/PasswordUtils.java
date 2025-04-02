package com.utopia.designmyexperience_api.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Utility class for hashing and verifying passwords using BCrypt.
 */
public class PasswordUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Hash a plain text password.
     *
     * @param plainPassword The plain password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Compare a plain password with a hashed one.
     *
     * @param plainPassword The plain password to check.
     * @param hashedPassword The hashed password from the database.
     * @return True if the password matches the hash, false otherwise.
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}